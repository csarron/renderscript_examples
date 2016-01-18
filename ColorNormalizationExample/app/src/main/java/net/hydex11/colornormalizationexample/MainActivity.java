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

package net.hydex11.colornormalizationexample;

import android.renderscript.*;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
