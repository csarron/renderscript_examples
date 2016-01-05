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

package net.hydex11.imageresizeexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.widget.ImageView;

import net.hydex11.surfacerenderexample.ScriptC_main;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Run example
        example();
    }

    private void example() {
        RenderScript mRS = RenderScript.create(this);

        // Our resize script
        ScriptC_main main = new ScriptC_main(mRS);

        // ImageViews that will handle input and output
        ImageView originalImageView = (ImageView) findViewById(R.id.imageView);
        ImageView nearestImageView = (ImageView) findViewById(R.id.imageView2);
        ImageView bicubicImageView = (ImageView) findViewById(R.id.imageView3);

        // Input image
        Bitmap inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.houseimage);
        originalImageView.setImageBitmap(inputImage);

        int inputWidth = inputImage.getWidth();
        int inputHeight = inputImage.getHeight();

        Allocation inputAllocation = Allocation.createFromBitmap(mRS, inputImage);

        // Output image will be wide as the screen and will scale proportionally
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        int outputWidth = size.x;
        int outputHeight = (int) ((float) outputWidth * ((float) inputHeight / (float) inputWidth));

        // Bitmaps that will handle the output
        Bitmap bitmapNearest = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);
        Bitmap bitmapBicubic = Bitmap.createBitmap(outputWidth, outputHeight, Bitmap.Config.ARGB_8888);

        main.set_inputWidth(inputWidth);
        main.set_inputHeight(inputHeight);
        main.invoke_setOutputSize(outputWidth, outputHeight);

        // Build output Allocations
        Type.Builder tbOutput = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tbOutput.setX(outputWidth);
        tbOutput.setY(outputHeight);
        Allocation nearestAllocation = Allocation.createTyped(mRS, tbOutput.create());
        Allocation bicubicAllocation = Allocation.createTyped(mRS, tbOutput.create());

        main.set_inputAllocation(inputAllocation);

        // Performs resizes
        main.forEach_resizeNearest(nearestAllocation);
        main.forEach_resizeBicubic(bicubicAllocation);

        // Copy results
        nearestAllocation.copyTo(bitmapNearest);
        bicubicAllocation.copyTo(bitmapBicubic);

        // Show results
        nearestImageView.setImageBitmap(bitmapNearest);
        bicubicImageView.setImageBitmap(bitmapBicubic);

        mRS.destroy();
    }
}
