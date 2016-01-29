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

package net.hydex11.profilerexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Starts the example
        example();
    }

    Thread exampleThread;

    private void example() {

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // Create a view to see LogCat log
        LogView logView = new LogView(this, Timings.TAG);
        logView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logView.addLogLine("Wait for logs. It is going to take some seconds...\n");

        linearLayout.addView(logView);

        // Set the only view button to kill our application
        Button endMe = (Button) findViewById(R.id.button);
        endMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exampleThread != null)
                    // We force the interruption of loop thread (may cause an exception,
                    // but this is just a RS example!
                    exampleThread.interrupt();

                System.exit(0);
            }
        });

        // As we are going over a loop, it is needed to not run it on UI thread, as we'd
        // get frozen window rendering. So, just make another one.
        exampleThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Instantiate our RS context
                final RenderScript mRS = RenderScript.create(MainActivity.this);

                // Load input image
                Bitmap inputBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.houseimage);

                // Instantiates the input allocation.
                Allocation inputAllocation = Allocation.createFromBitmap(mRS, inputBitmap);
                Allocation outputAllocation = Allocation.createTyped(mRS, inputAllocation.getType());

                // Instantiates our profiler
                Timings timings = new Timings();

                // Tells the profiler to call this function before taking each timing. This way
                // we are listening for previous kernel to really end.
                timings.setTimingCallback(new Timings.TimingCallback() {
                    @Override
                    public void run() {
                        mRS.finish();
                    }
                });

                // Averaging will run every 10 cycles
                timings.setTimingDebugInterval(10);

                // We create two different scripts, that has same kernels. First one is
                // standard RenderScript, second one uses FilterScript approach. This way
                // you can see differences in performance (please use an high end device to
                // completely notice the difference).
                ScriptC_main main = new ScriptC_main(mRS);
                ScriptC_main_fs main_fs = new ScriptC_main_fs(mRS);

                main.set_inputAllocation(inputAllocation);
                main_fs.set_inputAllocation(inputAllocation);

                main.set_width(inputBitmap.getWidth());
                main.set_height(inputBitmap.getHeight());
                main_fs.set_width(inputBitmap.getWidth());
                main_fs.set_height(inputBitmap.getHeight());

                // Tells the profiler to output debug data every 100 cycles
                timings.setTimingDebugInterval(100);

                // My loop
                while (true) {
                    // Calling this function, the profiler sets current time as initial one
                    timings.initTimings();

                    // Here we set the launch options for the kernels, to prevent the
                    // blur pointers from overflowing
                    Script.LaunchOptions launchOptions = new Script.LaunchOptions();

                    // Here we test three different sets of kernels, increasing the blur radius.
                    // The more it gets high, the more neighbor elements are accessed in the process.

                    // Blur 3x3
                    int blurRadius = 1;
                    main.set_blurRadius(blurRadius);
                    main_fs.set_blurRadius(blurRadius);
                    launchOptions.setX(blurRadius, inputBitmap.getWidth() - 1 - blurRadius);
                    launchOptions.setY(blurRadius, inputBitmap.getHeight() - 1 - blurRadius);

                    // Adds timing for kernel
                    main.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur3x3 - RenderScript");

                    main.forEach_pointerKernel(inputAllocation, outputAllocation, launchOptions);
                    timings.addTiming("blur3x3 - RenderScript (pointers)");

                    main_fs.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur3x3 - FilterScript");

                    // Blur 7x7
                    blurRadius = 3;
                    main.set_blurRadius(blurRadius);
                    main_fs.set_blurRadius(blurRadius);
                    launchOptions.setX(blurRadius, inputBitmap.getWidth() - 1 - blurRadius);
                    launchOptions.setY(blurRadius, inputBitmap.getHeight() - 1 - blurRadius);

                    // Adds timing for kernel
                    main.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur7x7 - RenderScript");

                    main.forEach_pointerKernel(inputAllocation, outputAllocation, launchOptions);
                    timings.addTiming("blur7x7 - RenderScript (pointers)");

                    main_fs.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur7x7 - FilterScript");

                    // Blur 15x15
                    blurRadius = 7;
                    main.set_blurRadius(blurRadius);
                    main_fs.set_blurRadius(blurRadius);
                    launchOptions.setX(blurRadius, inputBitmap.getWidth() - 1 - blurRadius);
                    launchOptions.setY(blurRadius, inputBitmap.getHeight() - 1 - blurRadius);

                    // Adds timing for kernel
                    main.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur15x15 - RenderScript");

                    main.forEach_pointerKernel(inputAllocation, outputAllocation, launchOptions);
                    timings.addTiming("blur15x15 - RenderScript (pointers)");

                    main_fs.forEach_root(outputAllocation, launchOptions);
                    timings.addTiming("blur15x15 - FilterScript");

                    // Checks if this cycle is the correct one for debugging timings and outputs them
                    // in case it is.
                    timings.debugTimings();

                    try {
                        // Small wait, to not overkill the CPU/GPU
                        Thread.sleep(10, 0);
                    } catch (InterruptedException e) {
                        // Will be caused by clicking on "End" button, as we will be interrupting
                        // this Thread brutally

                    }
                }
            }
        });
        exampleThread.start();
    }
}
