/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 - Alberto Marchetti <alberto.marchetti@hydex11.net>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.hydex11.cameracaptureexample;
import android.graphics.SurfaceTexture;
import android.renderscript.*;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;

/**
 * Created by Alberto on 28/12/2015.
 */
public class RSCompute {
    private static final String TAG = "RSCompute";

    RenderScript mRS;

    // Script vars
    ScriptIntrinsicYuvToRGB sYUV; // Used to convert input image from YUV to RGBA elements
    ScriptC_main scriptCMain; // Our custom script
    ScriptC_customYUVToRGBAConverter customYUVToRGBAConverter;

    // Boolean to use a custom YUV to RGB kernel, instead of the custom intrinsic script
    boolean useCustomYUVToRGBConversion = false;
    // Boolean to use the simple YUV type declaration.
    boolean useYUVType = true;

    // Surface that gets generated from TextureView
    Surface mSurface = null;

    // Allocations
    Allocation inputAllocation; // Camera preview YUV allocation
    Allocation outputAllocation; // Output allocation

    // Temporary intermediate allocation, used to store YUV to RGBA conversion output and
    // used as our custom script input
    Allocation midAllocation;

    Point mInputImageSize, mOutputAllocationSize;

    // Funcs
    public void compute(byte[] dataIn) {

        // Copies data from camera preview buffer
        inputAllocation.copyFrom(dataIn);

        // Converts input image to RGBA
        if (useCustomYUVToRGBConversion)
            customYUVToRGBAConverter.forEach_convert(midAllocation);
        else
            sYUV.forEach(midAllocation);

        // Executes our custom script calculations
        scriptCMain.forEach_root(outputAllocation);

        // Waits for all kernels to end
        mRS.finish();

        // Sends custom script output to rendering surface
        outputAllocation.ioSend();
    }

    public RSCompute(RenderScript rsContext, Point inputImageSize, Point outputAllocationSize) {
        mRS = rsContext;
        mInputImageSize = inputImageSize;
        mOutputAllocationSize = outputAllocationSize;

        // Initalizes Scripts
        if (useCustomYUVToRGBConversion)
            customYUVToRGBAConverter = new ScriptC_customYUVToRGBAConverter(mRS);
        else
            sYUV = ScriptIntrinsicYuvToRGB.create(mRS, Element.U8_4(mRS));

        scriptCMain = new ScriptC_main(mRS);

        // Init Allocations

        if(useYUVType){
            // When used together with
            Type.Builder yuvTypeBuilder = new Type.Builder(mRS, Element.createPixel(mRS, Element.DataType.UNSIGNED_8, Element.DataKind.PIXEL_YUV));
            yuvTypeBuilder.setX(inputImageSize.x);
            yuvTypeBuilder.setY(inputImageSize.y);
            yuvTypeBuilder.setYuvFormat(android.graphics.ImageFormat.NV21);
            Type yuvType = yuvTypeBuilder.create();
            inputAllocation = Allocation.createTyped(mRS, yuvType, Allocation.USAGE_SCRIPT);
        }
        else {
            // Calculates expected YUV bytes count as YUV is not a human friendly way of storing data:
            // https://en.wikipedia.org/wiki/YUV#Y.27UV420p_.28and_Y.27V12_or_YV12.29_to_RGB888_conversion
            int expectedBytes = inputImageSize.x * inputImageSize.y *
                    ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8;

            Type.Builder yuvTypeBuilder = new Type.Builder(mRS, Element.U8(mRS)).setX(expectedBytes);
            Type yuvType = yuvTypeBuilder.create();
            inputAllocation = Allocation.createTyped(mRS, yuvType, Allocation.USAGE_SCRIPT);
        }

        // Creates temporary allocation that will match camera preview size
        Type.Builder rgbaType = new Type.Builder(mRS, Element.RGBA_8888(mRS)).setX(mInputImageSize.x).setY(mInputImageSize.y);
        midAllocation = Allocation.createTyped(mRS, rgbaType.create(), Allocation.USAGE_SCRIPT);

        // Init compute
        if (useCustomYUVToRGBConversion) {
            customYUVToRGBAConverter.invoke_setInputImageSize(mInputImageSize.x, mInputImageSize.y);
            customYUVToRGBAConverter.set_inputAllocation(inputAllocation);
        } else
            sYUV.setInput(inputAllocation);

        scriptCMain.set_aIn(midAllocation);
        scriptCMain.set_sizeIn(new Int2(mInputImageSize.x, mInputImageSize.y)); // Tells the script camera preview size

        // Tells the script the resize ratio from input to output
        scriptCMain.set_scaleInv(1.0f / ((float) mOutputAllocationSize.x / (float) mInputImageSize.x));

        // Sets brightness threshold (0-255), so that gray values brighter than it will be turned to red.
        scriptCMain.set_threshold(180);

    }

    // Sets output TextureView, to be used as RenderScript rendering surface.
    // Requires also output allocation size, that will use previous TextureView as output surface.
    public void setRenderTextureView(TextureView textureView) {
        // If a previous surface was already defined, destroy it and
        // its associated RS allocation
        if (isValidHolder()) {
            destroyHolder();
        }

        textureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    // Main functions
    public boolean isValidHolder() {
        // Checks if current situation is good for using the output surface:
        // Surface must be instantiates as well as output Allocation
        return mSurface != null && outputAllocation != null;
    }

    // Resets output allocation, as rendering surface has changed
    private void resetAllocation(Surface surface) {
        Log.d(TAG, "resetAllocation called");

        synchronized (this) {
            // Destroys current out Allocation data if exists
            destroyAllocation();

            mSurface = surface;

            // Instantiates new output Allocation, whose size was determined before
            Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
            tb.setX(mOutputAllocationSize.x);
            tb.setY(mOutputAllocationSize.y);

            outputAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

            // Sets output surface for Allocation
            outputAllocation.setSurface(surface);
        }
    }

    // Util
    private void destroyHolder() {
        destroyAllocation();
        mSurface = null;
    }

    private void destroyAllocation() {
        // Destroys Allocation if was defined before
        if (outputAllocation != null) {
            synchronized (this) {
                // Waits for previous RenderScript kernels to finish, as otherwise could trigger
                // memory errors.
                mRS.finish();
                outputAllocation.destroy();
                outputAllocation = null;
            }
        }
    }

    // Callback that listens to TextureView status, to determine if it becames a valid surface
    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Surface mSurface = new Surface(surface);
            resetAllocation(mSurface);
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            resetAllocation(new Surface(surface));
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            destroyHolder();
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };
}
