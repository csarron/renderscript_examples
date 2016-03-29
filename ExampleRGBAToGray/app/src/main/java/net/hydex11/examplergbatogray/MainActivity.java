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

package net.hydex11.examplergbatogray;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import hydex11.net.examplergbatogray.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_main);

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

        mRS.destroy();
        return outBitmap;
    }
}
