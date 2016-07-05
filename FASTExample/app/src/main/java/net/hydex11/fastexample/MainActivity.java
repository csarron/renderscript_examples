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

import android.graphics.Bitmap;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.TextureView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    Timings timings;

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

    // Utility function to enable saving current screen
    boolean saveCurrentScreen = false;

    private void example() {
        timings = new Timings(this);

        // To calculate the timings' average on a different measurements set size, change
        // the following instruction
        timings.setTimingDebugInterval(50);

        // Prevent window dimming
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        // We load the NDK library we created. This library's code can be seen
        // inside app/src/main/jni folder. Its compiling cannot be done using
        // Android Studio default NDK build system, as it cannot include custom
        // CPP flags (we include the OpenMP library).
        // Please refer to the app/build.gradle file (last section of it) to understand
        // how the build process works.
        System.loadLibrary("native");

        // We instantiate the surfaces, camera preview one and RenderScript
        // preview one.
        cameraPreviewSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        rsResultTextureView = (TextureView) findViewById(R.id.textureView);

        // Instantiation of surfaces' callbacks, to trigger the execution of our
        // example.
        cameraPreviewSurfaceView.getHolder().addCallback(cameraPreviewCallback);
        rsResultTextureView.setSurfaceTextureListener(surfaceTextureListener);

        // As we are using a forced landscape mode (look at AndroidManifest.xml file),
        // we need the TextureView to rotate accordingly.
        rsResultTextureView.requestLayout();
        rsResultTextureView.invalidate();

        // Set the TextureView to use alpha channel, as it is overlapping the camera preview.
        rsResultTextureView.setOpaque(false);

        // Exit on surface touch
        rsResultTextureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    // This function gets called by surfaces' callbacks, as the surfaces become ready.
    private void checkSurfaces() {
        if (cameraPreviewSurfaceHolder != null && rsResultSurface != null) {
            // As surfaces are ready, we can instantiate the camera
            CameraHandler.CameraSetup cameraSetup = new CameraHandler.CameraSetup();
            cameraSetup.setMaxPreviewSize(new Point(1280, 720));
            cameraSetup.setCameraBuffersCount(1);
            cameraSetup.setPreviewSurfaceHolder(cameraPreviewSurfaceHolder);

            // Function that gets called every time there is a camera frame available.
            cameraSetup.setPreviewCallback(previewCallback);

            cameraHandler = new CameraHandler(this);
            try {
                cameraHandler.openCamera(cameraSetup);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            // Initialize RenderScript
            instantiateRS();
        }
    }

    // RS variables
    RenderScript mRS;
    boolean rsInstantiated = false;
    Camera.Size inputImageSize;

    Allocation inputAllocation;
    Allocation rgbAllocation;
    Allocation grayAllocation;
    Allocation fastKpAllocation; // Allocation where to store the detected keypoints
    Allocation outputAllocation; // Allocation where to display detected keypoints

    // Scripts
    ScriptIntrinsicYuvToRGB scriptIntrinsicYuvToRGB;
    ScriptC_customYUVToGrayscaleConverter customYUVToGrayscaleConverter;

    // This script contains a FAST detector written without any possible optimization,
    // to simply display how the detection process works at the root.
    ScriptC_fast_no_optimization scriptCFastNoOptimization;

    // This script contains a FAST detector, whose code has been ported directly from the official
    // FAST detection library (http://www.edwardrosten.com/work/fast.html).
    ScriptC_fast scriptCFast;

    // This script contains the direct porting of OpenCV's FAST detector, taken from
    // OpenCV source code (https://github.com/Itseez/opencv/blob/master/modules/cudafeatures2d/src/cuda/fast.cu).
    ScriptC_fast_opencv scriptCFastOpenCV;

    // Script that contains conversion to gray image and a function to display the detected keypoints.
    ScriptC_util scriptCUtil;

    // LaunchOptions
    Script.LaunchOptions fastLaunchOptions;

    private void instantiateRS() {
        mRS = RenderScript.create(this); //, RenderScript.ContextType.DEBUG);

        timings.setTimingCallback(new Timings.TimingCallback() {
            @Override
            public void run() {
                mRS.finish();
            }
        });

        inputImageSize = cameraHandler.getCameraSize();

        // Initialize holder for NDK FAST extraction implementation
        setImageSize(inputImageSize.width, inputImageSize.height);

        // Initialize RenderScript scripts
        scriptIntrinsicYuvToRGB = ScriptIntrinsicYuvToRGB.create(mRS, Element.RGBA_8888(mRS));
        customYUVToGrayscaleConverter = new ScriptC_customYUVToGrayscaleConverter(mRS);
        scriptCFastNoOptimization = new ScriptC_fast_no_optimization(mRS);
        scriptCFast = new ScriptC_fast(mRS);
        scriptCFastOpenCV = new ScriptC_fast_opencv(mRS);
        scriptCUtil = new ScriptC_util(mRS);

        // Build type for YUV input image
        Type.Builder tb;
        tb = new Type.Builder(mRS, Element.createPixel(mRS, Element.DataType.UNSIGNED_8, Element.DataKind.PIXEL_YUV));
        tb.setX(inputImageSize.width);
        tb.setY(inputImageSize.height);
        tb.setYuvFormat(android.graphics.ImageFormat.NV21);

        // Create input allocation, that will receive the camera frame
        inputAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT);
        scriptIntrinsicYuvToRGB.setInput(inputAllocation);
        customYUVToGrayscaleConverter.invoke_setInputImageSize(inputImageSize.width, inputImageSize.height);
        customYUVToGrayscaleConverter.set_inputAllocation(inputAllocation);

        // Build type for converted image (YUV to RGBA)
        tb = new Type.Builder(mRS, Element.RGBA_8888(mRS)).setX(inputImageSize.width).setY(inputImageSize.height);
        Type rgbaType = tb.create();
        // Build type for converted image (RGBA to GRAY)
        tb = new Type.Builder(mRS, Element.U8(mRS)).setX(inputImageSize.width).setY(inputImageSize.height);
        Type grayType = tb.create();

        // Define all allocations
        rgbAllocation = Allocation.createTyped(mRS, rgbaType, Allocation.USAGE_SCRIPT);
        grayAllocation = Allocation.createTyped(mRS, grayType, Allocation.USAGE_SCRIPT);
        fastKpAllocation = Allocation.createTyped(mRS, grayType, Allocation.USAGE_SCRIPT);
        outputAllocation = Allocation.createTyped(mRS, rgbaType, Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

        // Tells the output allocation which surface is its
        outputAllocation.setSurface(rsResultSurface);

        // Prepare RS scripts
        scriptCFastNoOptimization.set_grayAllocation(grayAllocation);
        //scriptCFast.set_grayAllocation(grayAllocation);
        scriptCFast.set_grayAllocation(grayAllocation);
        scriptCFastOpenCV.set_grayAllocation(grayAllocation);

        // Defines limits for RS kernels execution, as FAST extraction requires
        // a border of 3 pixels to operate and harris score requires 4 of them, so
        // the maximum is chosen.
        fastLaunchOptions = new Script.LaunchOptions();
        fastLaunchOptions.setX(3, inputImageSize.width - 3);
        fastLaunchOptions.setY(3, inputImageSize.height - 3);

        // Settings this to true tells the camera preview callback that the RS code
        // can be executed
        rsInstantiated = true;
    }

    // Camera callback
    Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            if (rsInstantiated) {

                // Initialize profiling
                timings.initTimings();

                // Copy image from camera frame
                inputAllocation.copyFrom(data);
                timings.addTiming("Camera data copy");

                // Converts image, YUV -> RGBA -> GRAY
                scriptIntrinsicYuvToRGB.forEach(rgbAllocation);
                scriptCUtil.forEach_rgbaToGray(rgbAllocation, grayAllocation);
                timings.addTiming("YUV to grayscale (RenderScriptIntrinsic)");

                // Converts image, YUV -> GRAY
                customYUVToGrayscaleConverter.forEach_convert(grayAllocation);
                timings.addTiming("YUV to grayscale (RenderScript)");

                // RS FAST (not optimized)
                // To be used only to understand how FAST extraction works. It has no
                // optimizations at all. Do not use it for benchmark purposes because it
                // is terribly slow. Turn to true to enable it.
                if (false) {
                    scriptCFastNoOptimization.forEach_fastNoOptimized(grayAllocation, fastKpAllocation, fastLaunchOptions);
                    timings.addTiming("RenderScript FAST (no optimization)");
                }

                // RS FAST (optimized)
                scriptCFast.forEach_fastOptimized(grayAllocation, fastKpAllocation, fastLaunchOptions);
                timings.addTiming("RenderScript FAST lib");

                // RS FAST (OpenCV porting)
                scriptCFastOpenCV.forEach_fastOpenCV(grayAllocation, fastKpAllocation, fastLaunchOptions);
                timings.addTiming("RenderScript FAST (OpenCV porting)");

                // FAST library extraction
                yuvToGray(data);
                timings.addTiming("YUV to grayscale (NDK)");

                fastLibExtraction();
                timings.addTiming("NDK FAST lib (optimized)");

                // Displays keypoints on preview surface
                scriptCUtil.forEach_showFastKeypoints(fastKpAllocation, outputAllocation);
                outputAllocation.ioSend();
                timings.addTiming("Show keypoints");

                timings.debugTimings();

                // Save screens
                if(saveCurrentScreen)
                {
                    try {
                        String fileName = String.valueOf(System.currentTimeMillis());
                        Bitmap tmpBitmap = Bitmap.createBitmap(inputImageSize.width, inputImageSize.height, Bitmap.Config.ARGB_8888);

                        // Save input image
                        rgbAllocation.copyTo(tmpBitmap);
                        Util.saveImageToExternal(MainActivity.this, fileName + "-rgba", tmpBitmap);

                        // Save gray image
                        scriptCUtil.forEach_grayToRGBA(grayAllocation, outputAllocation);
                        outputAllocation.copyTo(tmpBitmap);
                        Util.saveImageToExternal(MainActivity.this, fileName + "-gray", tmpBitmap);

                        // Save FAST image
                        scriptCUtil.forEach_showFastKeypoints(fastKpAllocation, outputAllocation);
                        outputAllocation.copyTo(tmpBitmap);
                        Util.saveImageToExternal(MainActivity.this, fileName + "-fast", tmpBitmap);

                        Toast.makeText(MainActivity.this,"Images saved", Toast.LENGTH_SHORT).show();

                    }
                    catch (Exception ex)
                    {
                        ex.printStackTrace();
                    }

                    saveCurrentScreen = false;
                }

            }
            camera.addCallbackBuffer(data);
        }
    };

    // NDK FAST library extraction
    private native int setImageSize(int width, int height);

    private native int yuvToGray(byte[] data);

    private native int fastLibExtraction();

    // Surfaces callbacks, to initialize the process
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

    // Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.saveScreen:
                saveCurrentScreen = true;
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
