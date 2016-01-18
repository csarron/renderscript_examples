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

import android.app.Activity;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.hardware.Camera;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import android.view.SurfaceHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alberto on 27/12/2015.
 */
public class CameraHandler {

    static final String TAG = "CameraHandler";

    Activity mActivity;
    Camera mCamera;
    Camera.CameraInfo mBackCameraInfo;
    Camera.Parameters mCameraParameters;
    int expectedCameraBytes;
    Camera.Size mCameraSize;

    // Init
    public CameraHandler(Activity activity) {
        mActivity = activity;
        Log.d(TAG, "Instantiated new CameraHandler");
    }

    public Camera.Size getCameraSize() {
        if (mCameraSize == null)
            throw new RuntimeException("getCameraSize must be called after openCamera");
        return mCameraSize;
    }

    // Funcs

    public void openCamera(CameraSetup cameraSetup) throws Exception {

        // Check camera setup
        cameraSetup.checkSetup();

        // Gets back camera
        Pair<Camera.CameraInfo, Integer> backCamera = getBackCamera();
        final int backCameraId = backCamera.second;
        mBackCameraInfo = backCamera.first;

        // Tries to open camera
        mCamera = Camera.open(backCameraId);

        // Check display orientation and fixes camera image.
        // Without this fix, for example, in landscape mode you would get a stretched preview
        setDisplayOrientation();

        // Sets up preview surface
        mCamera.setPreviewDisplay(cameraSetup.holder);

        // Sets up callback for camera, that will get input YUV NV21 bytes
        mCamera.setPreviewCallbackWithBuffer(cameraSetup.previewCallback);

        mCameraParameters = mCamera.getParameters();

        // Gets supported previews sizes and uses chooseOptimalPreviewSize to set maximum preview
        // size and ratio.
        List<Camera.Size> supportedPreviewSizes = mCameraParameters.getSupportedPreviewSizes();
        mCameraSize = chooseOptimalPreviewSize(supportedPreviewSizes, cameraSetup.maxPreviewSize.x, cameraSetup.maxPreviewSize.y);

        // Calculates expected camera bytes length, as YUV image uses compressed stored image.
        expectedCameraBytes = mCameraSize.width * mCameraSize.height *
                ImageFormat.getBitsPerPixel(ImageFormat.NV21) / 8;

        int bufferSize = expectedCameraBytes;

        // For each camera buffer, adds corresponding byte array.
        // Camera buffers are used to store temporarily images to process in an async way.
        for (int i = 0; i < cameraSetup.cameraBuffersCount; i++) {
            byte[] cameraBuffer = new byte[bufferSize];
            mCamera.addCallbackBuffer(cameraBuffer);
        }

        // Forces camera preview size, as got earlier.
        mCameraParameters.setPreviewSize(mCameraSize.width, mCameraSize.height);

        // Sets previously set camera parameters
        mCamera.setParameters(mCameraParameters);

        // Starts preview
        mCamera.startPreview();

    }

    public void destroy() {
        if (mCamera != null) {
            mCamera.stopPreview();
            mCamera.release();
        }
    }

    public Camera.Parameters getCameraParameters() {
        return mCameraParameters;
    }

    // Utils
    private Camera.Size chooseOptimalPreviewSize(List<Camera.Size> choices, int width, int height) {
        // Collect the supported resolutions that are at least as big as the preview Surface
        List<Camera.Size> bigEnough = new ArrayList<>();

        float ratio = (float) height / (float) width;

        for (Camera.Size option : choices) {
            if (option.height == option.width * ratio &&
                    option.width >= width && option.height >= height) {
                bigEnough.add(option);
            }
        }

        // Pick the smallest of those, assuming we found any
        if (bigEnough.size() > 0) {
            Camera.Size chosenCameraSize = Collections.min(bigEnough, new CompareSizesByArea());
            Log.d(TAG, "Chose maximum preview size to " + chosenCameraSize);
            return chosenCameraSize;
        } else {
            throw new RuntimeException("Couldn't find any suitable preview size");
        }
    }

    // Taken from https://github.com/pinguo-yuyidong/Camera2/blob/master/camera2/src/main/java/us/yydcdut/camera2/CompareSizesByArea.java
    static class CompareSizesByArea implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            // We cast here to ensure the multiplications won't overflow
            return Long.signum((long) lhs.width * lhs.height -
                    (long) rhs.width * rhs.height);
        }

    }

    private Pair<Camera.CameraInfo, Integer> getBackCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        final int numberOfCameras = Camera.getNumberOfCameras();

        // Iterates through each camera and, if camera id is for back camera, returns its index and info
        for (int i = 0; i < numberOfCameras; ++i) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                return new Pair<Camera.CameraInfo, Integer>(cameraInfo,
                        Integer.valueOf(i));
            }
        }
        return null;
    }

    private void setDisplayOrientation() {
        // Uses window manager to get current window rotation, and rotates camera image accordingly
        final int rotation = mActivity.getWindowManager()
                .getDefaultDisplay().getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = 90;
                break;
            case Surface.ROTATION_180:
                degrees = 180;
                break;
            case Surface.ROTATION_270:
                degrees = 270;
                break;
        }

        final int displayOrientation = (mBackCameraInfo.orientation
                - degrees + 360) % 360;
        mCamera.setDisplayOrientation(displayOrientation);
    }

    static class CameraSetup {
        private SurfaceHolder holder;
        private Camera.PreviewCallback previewCallback;
        private Point maxPreviewSize;
        private int cameraBuffersCount = 1;

        public void setPreviewSurfaceHolder(SurfaceHolder _holder) {
            holder = _holder;
        }

        public void setPreviewCallback(Camera.PreviewCallback _previewCallback) {
            previewCallback = _previewCallback;
        }

        public void setMaxPreviewSize(Point _maxPreviewSize) {
            maxPreviewSize = _maxPreviewSize;
        }

        public void setCameraBuffersCount(int num) {
            cameraBuffersCount = num;
        }

        public void checkSetup() {
            if (maxPreviewSize == null)
                throw new RuntimeException("Must select max preview size");
            if (holder == null)
                throw new RuntimeException("Must select preview holder");
            if (cameraBuffersCount <= 0)
                throw new RuntimeException("Camera buffers min count is 1");
            if (previewCallback == null)
                throw new RuntimeException("Must select preview callback function");

            Log.d(TAG, "CameraSetup is ok");
        }
    }
}
