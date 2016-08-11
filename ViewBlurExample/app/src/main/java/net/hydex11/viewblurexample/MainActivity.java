package net.hydex11.viewblurexample;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.SurfaceTexture;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    // View objects
    Button blurItButton;
    View relativeLayout;

    // RenderScript bound objects
    RenderScript mRS;
    ScriptIntrinsicBlur scriptIntrinsicBlur;
    Allocation allocOriginalScreenshot, allocBlurred;
    TextureView textureViewBlurred;

    /*
    The purpose of this example is to show how RenderScript can be used to blur a generic view.
    What the example does (when the button "Blur it!" gets clicked) is:

    1) Blurs the chosen view

    The chosen view, for this example, must be contained inside a bigger container (ex. a LinearLayout).
    The blur process:

    * Gets a screenshot of the view.
    * Instantiates all RenderScript allocations (one for input and one for blur output).
    * Blurs the screenshot using the ScriptIntrinsicBlur class.
    * Creates a TextureView to display the blur result.
    * Substitutes the new TextureView with the original view. In this process, the original view
      gets saved inside the "tag" field of the new TextureView, so that later it can be restored.

    2) Display a simple dialog
    3) Unblur the original view

    The unblur process removes the TextureView from the layout and restores the original View.

     */
    void example() {

        // Initialize RenderScript
        mRS = RenderScript.create(this);
        scriptIntrinsicBlur = ScriptIntrinsicBlur.create(mRS, Element.RGBA_8888(mRS));
        scriptIntrinsicBlur.setRadius(5);

        // Initialize views
        blurItButton = (Button) findViewById(R.id.button);
        relativeLayout = findViewById(R.id.relativeLayout);

        blurItButton.setOnClickListener(onBlurItClickListener);
    }

    View.OnClickListener onBlurItClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            // Blurs the underneath view
            blurView(relativeLayout);

            // Shows an example overview dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Simple message")
                    .setCancelable(false)
                    .setMessage("Hello blurred view!")
                    .setPositiveButton("Close", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            // Restores underneath view
                            unblurView(relativeLayout);
                        }
                    });
            builder.create().show();
        }
    };

    Bitmap getViewScreenshot(View v) {
        v.setDrawingCacheEnabled(true);
        Bitmap b = Bitmap.createBitmap(v.getDrawingCache());
        v.setDrawingCacheEnabled(false);

        return b;
    }

    /*
    This function replaces a view with another, mimicking the original one's layout parameters,
    to look the same.
    Also, it saves the new view object in the original one's tag field, to be able to restore
    the original view later.
     */
    void replaceView(View originalView, View newView) {
        originalView.setTag(newView);

        newView.setLayoutParams(new FrameLayout.LayoutParams(originalView.getLayoutParams()));

        ViewGroup parent = (ViewGroup) originalView.getParent();
        int index = parent.indexOfChild(originalView);
        parent.removeView(originalView);

        parent.addView(newView, index);
    }

    /*
    Restores the original view. Checks if there is a valid view inside the tag field, and restores
    it.
     */
    void restoreView(View v) {
        View otherView = (View) v.getTag();

        if (otherView != null && otherView.getParent() != null) {
            replaceView(otherView, v);
        } else if (v != null && v.getParent() != null) {
            replaceView(v, otherView);
        }
    }

    void blurView(View v) {
        // First of all, take a screen of the current view
        Bitmap viewScreenshot = getViewScreenshot(v);

        // Defines allocations where to store the screenshot and the temporary blurred image
        if (allocOriginalScreenshot != null && (allocOriginalScreenshot.getType().getX() != viewScreenshot.getWidth() ||
                allocOriginalScreenshot.getType().getY() != viewScreenshot.getHeight())) {

            // Current allocations have wrong sizes!
            allocOriginalScreenshot.destroy();
            allocBlurred.destroy();

            textureViewBlurred = null;
            allocOriginalScreenshot = null;
            allocBlurred = null;
        }

        if (allocOriginalScreenshot == null) {
            allocOriginalScreenshot = Allocation.createFromBitmap(mRS, viewScreenshot);
            // Creates an allocation where to store the blur results
            allocBlurred = Allocation.createTyped(mRS, allocOriginalScreenshot.getType(), Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

            // Then, replace the current view with a TextureView.
            // This lets RenderScript display the blurred screenshot with ease, without killing
            // the memory (in case of large screenshots)
            textureViewBlurred = new TextureView(this);
            textureViewBlurred.setOpaque(false);
            textureViewBlurred.setSurfaceTextureListener(surfaceTextureListener);

        } else {
            // Just copy the new view screenshot
            allocOriginalScreenshot.copyFrom(viewScreenshot);
        }

        replaceView(v, textureViewBlurred);

    }

    void unblurView(View v) {
        restoreView(v);
    }

    // Performs the actual blur calculation
    void executeBlur() {
        Log.d(TAG, "Executing blur");

        scriptIntrinsicBlur.setInput(allocOriginalScreenshot);
        scriptIntrinsicBlur.forEach(allocBlurred);

        allocBlurred.ioSend();
    }

    /*
    This is the TextureView listener, and it uses the following "trick":
    every time the TextureView gets added to a ViewGroup, the surface is re-instantiates. This means
    that the onSurfaceTextureAvailable callback gets called every time. We can bound the blur calc
    process directly to this callback.
     */
    TextureView.SurfaceTextureListener surfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
            // Once the surface is ready, execute the blur
            allocBlurred.setSurface(new Surface(surfaceTexture));

            executeBlur();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

        }
    };

}
