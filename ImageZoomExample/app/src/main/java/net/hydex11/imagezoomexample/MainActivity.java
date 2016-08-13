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

package net.hydex11.imagezoomexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Run example
        example();
    }

    // Example code
    RenderScript mRS;
    Allocation inputAllocation;
    Allocation outputAllocation;
    ScriptC_magnifier magnifier;

    ImageView originalImageView;
    ImageView zoomedImageView;

    Bitmap inputImage, outputImage;

    private void example() {
        // ImageViews that will handle input and output
        originalImageView = (ImageView) findViewById(R.id.imageView);
        zoomedImageView = (ImageView) findViewById(R.id.imageView2);

        // Initialize RenderScript context
        initRS();

        // Perform first magnification
        magnify();

        // Set up a click listener on the magnified image.
        // When touched, the magnifier will be moved to the touch position.
        zoomedImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int touchX = (int) event.getX();
                int touchY = (int) event.getY();

                Log.d("Touch", String.format("Touch: %d, %d", touchX, touchY));

                magnifier.set_atX(touchX);
                magnifier.set_atY(touchY);

                magnify();

                return false;
            }
        });
    }

    private void initRS() {
        mRS = RenderScript.create(this);

        // Our magnifier script
        magnifier = new ScriptC_magnifier(mRS);

        // Input image
        inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.houseimage);
        originalImageView.setImageBitmap(inputImage);
        inputAllocation = Allocation.createFromBitmap(mRS, inputImage);
        outputAllocation = Allocation.createTyped(mRS, inputAllocation.getType());

        // Initializes magnifier
        magnifier.set_inputAllocation(inputAllocation);
        magnifier.set_atX(300);
        magnifier.set_atY(230);
        magnifier.set_radius(100);
        magnifier.set_scale(3);
    }

    private void magnify() {
        // Run the kernel
        magnifier.forEach_magnify(inputAllocation, outputAllocation);

        // Displays the magnification output
        outputImage = Bitmap.createBitmap(inputImage.getWidth(), inputImage.getHeight(), Bitmap.Config.ARGB_8888);
        outputAllocation.copyTo(outputImage);

        zoomedImageView.setImageBitmap(outputImage);
    }
}
