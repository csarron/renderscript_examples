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

package net.hydex11.fastexample;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Timings timings = new Timings();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    CameraHandler cameraHandler;
    SurfaceView cameraPreviewSurfaceView;
    SurfaceHolder cameraPreviewSurfaceHolder;

    TextureView rsResultTextureView;
    Surface rsResultSurface;

    private void example() {
        // Prevent window dimming
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        System.loadLibrary("native");

        cameraPreviewSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        rsResultTextureView = (TextureView) findViewById(R.id.textureView);

        cameraPreviewSurfaceView.getHolder().addCallback(cameraPreviewCallback);
        rsResultTextureView.setSurfaceTextureListener(surfaceTextureListener);

        rsResultTextureView.requestLayout();
        rsResultTextureView.invalidate();
        rsResultTextureView.setOpaque(false);

        rsResultTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    private void checkSurfaces() {
        if (cameraPreviewSurfaceHolder != null && rsResultSurface != null) {
            // As surfaces are ready, we can instantiate camera
            CameraHandler.CameraSetup cameraSetup = new CameraHandler.CameraSetup();
            cameraSetup.setMaxPreviewSize(new Point(1280, 720));
            cameraSetup.setCameraBuffersCount(1);
            cameraSetup.setPreviewSurfaceHolder(cameraPreviewSurfaceHolder);
            cameraSetup.setPreviewCallback(previewCallback);

            cameraHandler = new CameraHandler(this);
            try {
                cameraHandler.openCamera(cameraSetup);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            instantiateRS();
        }
    }

//    // OpenCV variables
//    OpenCVHolder openCVHolder;
//    byte grayOpenCVData[];

    // JNI FAST library array
    byte grayData[];

    // RS variables
    RenderScript mRS;
    boolean rsInstantiated = false;
    Camera.Size inputImageSize;

    Allocation inputAllocation;
    Allocation rgbAllocation;
    Allocation grayAllocation;
    Allocation fastKpAllocation;
    Allocation outputAllocation;

    // Scripts
    ScriptIntrinsicYuvToRGB scriptIntrinsicYuvToRGB;
    ScriptC_fast_no_optimization scriptCFastNoOptimization;
    ScriptC_fast scriptCFast;
    ScriptC_util scriptCUtil;

    // LaunchOptions
    Script.LaunchOptions fastLaunchOptions;

    private void instantiateRS() {
        mRS = RenderScript.create(this);

        inputImageSize = cameraHandler.getCameraSize();

        // Initialize OpenCV
//        openCVHolder = new OpenCVHolder(this, inputImageSize.width, inputImageSize.height);
//        grayOpenCVData = new byte[inputImageSize.width * inputImageSize.height];

        // Initialize holder for JNI FAST extraction implementation
        setImageSize(inputImageSize.width, inputImageSize.height);
        grayData = new byte[inputImageSize.width * inputImageSize.height];

        scriptIntrinsicYuvToRGB = ScriptIntrinsicYuvToRGB.create(mRS, Element.RGBA_8888(mRS));
        scriptCFastNoOptimization = new ScriptC_fast_no_optimization(mRS);
        scriptCFast = new ScriptC_fast(mRS);
        scriptCUtil = new ScriptC_util(mRS);

        Type.Builder tb;
        tb = new Type.Builder(mRS, Element.createPixel(mRS, Element.DataType.UNSIGNED_8, Element.DataKind.PIXEL_YUV));
        tb.setX(inputImageSize.width);
        tb.setY(inputImageSize.height);
        tb.setYuvFormat(android.graphics.ImageFormat.NV21);

        inputAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT);
        scriptIntrinsicYuvToRGB.setInput(inputAllocation);

        tb = new Type.Builder(mRS, Element.RGBA_8888(mRS)).setX(inputImageSize.width).setY(inputImageSize.height);
        Type rgbaType = tb.create();
        tb = new Type.Builder(mRS, Element.U8(mRS)).setX(inputImageSize.width).setY(inputImageSize.height);
        Type grayType = tb.create();

        rgbAllocation = Allocation.createTyped(mRS, rgbaType, Allocation.USAGE_SCRIPT);
        grayAllocation = Allocation.createTyped(mRS, grayType, Allocation.USAGE_SCRIPT);
        fastKpAllocation = Allocation.createTyped(mRS, grayType, Allocation.USAGE_SCRIPT);
        outputAllocation = Allocation.createTyped(mRS, rgbaType, Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);
        outputAllocation.setSurface(rsResultSurface);

        scriptCUtil.set_rgbAllocation(rgbAllocation);
        scriptCUtil.set_fastKpAllocation(fastKpAllocation);

        scriptCFastNoOptimization.set_grayAllocation(grayAllocation);
        scriptCFast.invoke_makeOffsets(inputImageSize.width);

        fastLaunchOptions = new Script.LaunchOptions();
        fastLaunchOptions.setX(3, inputImageSize.width - 4);
        fastLaunchOptions.setY(3, inputImageSize.height - 4);

        rsInstantiated = true;
    }

    // Camera callback
    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            if (rsInstantiated) {

                timings.initTimings();

                inputAllocation.copyFrom(data);

                scriptIntrinsicYuvToRGB.forEach(rgbAllocation);
                scriptCUtil.forEach_rgbToGray(grayAllocation);

                // Init OpenCV Mat data
//                grayAllocation.copyTo(grayOpenCVData);

                grayAllocation.copyTo(grayData);

                mRS.finish();
                timings.addTiming("Gray conversion completed");

                // RS FAST (not optimized)
                scriptCFastNoOptimization.forEach_fastNoOptimized(grayAllocation, fastKpAllocation, fastLaunchOptions);
                mRS.finish();
                timings.addTiming("RenderScript FAST (no optimization)");

                // RS FAST (optimized)
                scriptCFast.forEach_fastOptimized(grayAllocation, fastKpAllocation, fastLaunchOptions);
                mRS.finish();
                timings.addTiming("RenderScript FAST (optmized)");

                // FAST library extraction
                fastLibExtraction(grayData);
                timings.addTiming("Original FAST lib (optimized)");

                // OpenCV Fast
//                openCVHolder.parseFrame(timings, grayOpenCVData);

                timings.debugTimings();

                scriptCUtil.forEach_showFastKeypoints(outputAllocation);

                outputAllocation.ioSend();

            }
            camera.addCallbackBuffer(data);
        }
    };

    // JNI FAST LIB extraction
    // fastLibExtraction(JNIEnv *env, jobject, jint width, jint height, jbyteArray grayDataArray)
    private native int setImageSize(int width, int height);
    private native int fastLibExtraction(byte[] data);

    // Callbacks
    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            rsResultSurface = new Surface(surface);
            checkSurfaces();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    SurfaceHolder.Callback cameraPreviewCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            cameraPreviewSurfaceHolder = holder;
            checkSurfaces();
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    };
}
