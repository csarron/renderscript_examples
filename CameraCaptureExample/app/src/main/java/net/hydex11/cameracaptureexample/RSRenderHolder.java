package net.hydex11.cameracaptureexample;

import android.graphics.Point;
import android.graphics.SurfaceTexture;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;

/**
 * Created by Alberto on 28/12/2015.
 */
public class RSRenderHolder {

    private static final String TAG = "RSRenderHolder";

    private Surface mSurface = null;
    private RenderScript mRS = null;
    private Allocation mHolderAllocation = null;
    private Point mAllocationOutSize = null;

    public RSRenderHolder(RenderScript rsContext) {
        mRS = rsContext;
    }

    // Main functions
    public boolean isValidHolder() {
        // Checks if current situation is good for using the output surface:
        // Surface must be instantiates as well as output Allocation
        return mSurface != null && mHolderAllocation != null;
    }

    // Sets output TextureView, to be used as RenderScript rendering surface.
    // Requires also output allocation size, that will use previous TextureView as output surface.
    public void setRenderTextureView(TextureView textureView, Point allocationOutSize) {
        if (isValidHolder()) {
            // Destroy previous if exists
            destroyHolder();
        }

        mAllocationOutSize = allocationOutSize;
        textureView.setSurfaceTextureListener(mSurfaceTextureListener);
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
            tb.setX(mAllocationOutSize.x);
            tb.setY(mAllocationOutSize.y);

            mHolderAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

            // Sets output surface for Allocation
            mHolderAllocation.setSurface(surface);
        }
    }

    public Allocation getRSRenderHolderAllocation() {
        return mHolderAllocation;
    }


    private TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            resetAllocation(new Surface(surface));
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


    // Util

    private void destroyHolder() {
        destroyAllocation();
        mSurface = null;
    }

    private void destroyAllocation() {
        // Destroys Allocation if was defined before
        if (mHolderAllocation != null) {
            synchronized (this) {
                // Waits for previous RenderScript kernels to finish, as otherwise could trigger
                // memory errors.
                mRS.finish();
                mHolderAllocation.destroy();
                mHolderAllocation = null;
            }
        }
    }


}
