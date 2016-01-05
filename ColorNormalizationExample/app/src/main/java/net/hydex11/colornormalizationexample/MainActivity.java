package net.hydex11.colornormalizationexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        example();
    }

    private void example() {

        // Instantiates RenderScript and our script
        RenderScript mRS = RenderScript.create(this);
        ScriptC_main main = new ScriptC_main(mRS);

        // Loads input image
        Bitmap inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.photo_wide);

        // Loads views
        ImageView imageViewInput = (ImageView) findViewById(R.id.imageView);
        ImageView imageViewOutput = (ImageView) findViewById(R.id.imageView2);

        imageViewInput.setImageBitmap(inputImage);

        // Fills input allocation with source image
        Allocation inputAllocation = Allocation.createFromBitmap(mRS, inputImage);

        // Builds output allocation
        Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tb.setX(inputImage.getWidth());
        tb.setY(inputImage.getHeight());
        Allocation outputAllocation = Allocation.createTyped(mRS, tb.create());

        // Normalizes image
        main.forEach_normalizeImage(inputAllocation, outputAllocation);

        // Displays output image in view
        Bitmap outputImage = Bitmap.createBitmap(inputImage.getWidth(), inputImage.getHeight(), Bitmap.Config.ARGB_8888);
        outputAllocation.copyTo(outputImage);

        imageViewOutput.setImageBitmap(outputImage);
    }
}
