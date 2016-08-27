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

package net.hydex11.limitedconvolutionexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsicConvolve3x3;
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

    /*
    This example:

    1) Applies a convolution over an input image, restricting the convolution area to a certain
        limited one.
    2) Copies the convolution output to an output allocation.
     */
    private void example() {

        RenderScript mRS = RenderScript.create(this);

        // Loads input image
        Bitmap inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.houseimage);

        // Defines all allocations
        Allocation inputAllocation = Allocation.createFromBitmap(mRS, inputImage);
        Allocation convolvedAllocation = Allocation.createTyped(mRS, inputAllocation.getType());

        // This are the bounds of execution for the allocation (specifically for this example,
        // the area is where the house is in the image).

        // This is the start position of the convolution
        int outIndexX = 253;
        int outIndexY = 81;
        // This is the extension of the convolution [x, x+width) -> [outIndexX, outIndexX + outSizeX)
        int outSizeX = 59;
        int outSizeY = 56;

        Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tb.setX(outSizeX);
        tb.setY(outSizeY);
        Allocation outputAllocation = Allocation.createTyped(mRS, tb.create());

        // --- Pre fill output allocation with black color for debug purposes
        ScriptC_main scriptC_main = new ScriptC_main(mRS);
        scriptC_main.forEach_fillWithBlack(convolvedAllocation);
        scriptC_main.forEach_fillWithBlack(outputAllocation);

        // --- Convolution section

        // Defines the execution limits
        Script.LaunchOptions launchOptions = new Script.LaunchOptions();
        launchOptions.setX(outIndexX, outIndexX + outSizeX);
        launchOptions.setY(outIndexY, outIndexY + outSizeY);

        // Define the convolution
        ScriptIntrinsicConvolve3x3 convolve3x3 = ScriptIntrinsicConvolve3x3.create(mRS, Element.RGBA_8888(mRS));

        // Some coefficients
        float[] coefficients = {
                0.7f, 0, 0.5f,
                0, 1.0f, 0,
                0.5f, 0, 1.0f
        };
        convolve3x3.setCoefficients(coefficients);

        // Execute the allocation with limits
        convolve3x3.setInput(inputAllocation);
        convolve3x3.forEach(convolvedAllocation, launchOptions);

        // --- Output section, copies the convolution result to outputAllocation
        scriptC_main.set_inputAllocation(convolvedAllocation);
        scriptC_main.set_outIndexX(outIndexX);
        scriptC_main.set_outIndexY(outIndexY);

        scriptC_main.forEach_copyAllocation(outputAllocation);

        // --- Display all stages
        Bitmap intermediateImage = Bitmap.createBitmap(inputImage.getWidth(), inputImage.getHeight(), Bitmap.Config.ARGB_8888);
        Bitmap outputImage = Bitmap.createBitmap(outSizeX, outSizeY, Bitmap.Config.ARGB_8888);

        convolvedAllocation.copyTo(intermediateImage);
        outputAllocation.copyTo(outputImage);

        ((ImageView) findViewById(R.id.imageView)).setImageBitmap(inputImage);
        ((ImageView) findViewById(R.id.imageView2)).setImageBitmap(intermediateImage);
        ((ImageView) findViewById(R.id.imageView3)).setImageBitmap(outputImage);

    }
}
