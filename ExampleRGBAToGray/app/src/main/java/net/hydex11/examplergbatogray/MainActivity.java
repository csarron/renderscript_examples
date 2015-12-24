package net.hydex11.examplergbatogray;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import hydex11.net.examplergbatogray.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Triggers RenderScript process
        ImageView originalImageView = (ImageView) findViewById(R.id.imageView2);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);

        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.mipmap.ic_launcher);

        originalImageView.setImageBitmap(icon);

        Bitmap out = testRenderScript(this, icon);

        imageView.setImageBitmap(out);
    }


    Bitmap testRenderScript(Activity mActivity, Bitmap inBitmap) {

        // Creates a RS context.
        RenderScript mRS = RenderScript.create(mActivity);

        // Creates the input Allocation and copies all Bitmap contents into it.
        Allocation inAllocation = Allocation.createFromBitmap(mRS, inBitmap);

        // Defines the output Type, which will be a RGBA pixel.
        // The Allocation will be composed by four unsigned chars (0-255) for each pixel,
        // so that R-G-B-A values can be stored.
        // It is necessary to use a Type-based approach whenever there is a multi-dimensional sizing (X,Y).
        int bitmapWidth = inBitmap.getWidth();
        int bitmapHeight = inBitmap.getHeight();

        Type.Builder outType = new Type.Builder(mRS, Element.RGBA_8888(mRS)).setX(bitmapWidth).setY(bitmapHeight);

        // Creates the output Allocation wherein to store the conversion result.
        Allocation outAllocation = Allocation.createTyped(mRS, outType.create(), Allocation.USAGE_SCRIPT);

        // Creates the conversion script wrapper.
        ScriptC_exampleRGBAToGray conversionScript = new ScriptC_exampleRGBAToGray(mRS);

        // Binds the inAllocation variable with the actual Allocation.
        conversionScript.set_inAllocation(inAllocation);

        // Performs the conversion. RS kernel will use outAllocation size for its iterations.
        conversionScript.forEach_convertRGBAToGray(outAllocation);

        // Creates output Bitmap, matching input one size.
        Bitmap outBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, inBitmap.getConfig());

        // Copy calculation result to the output Bitmap.
        outAllocation.copyTo(outBitmap);

        return outBitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
