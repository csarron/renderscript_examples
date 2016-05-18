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

package net.hydex11.largesetexample;

import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "LargeSetExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Prevent window dimming
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // Create a view to see LogCat log
        LogView logView = new LogView(this, new String[]{ Timings.TAG, TAG}, 5);
        logView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logView.addLogLine("Wait for logs. It is going to take some seconds...\n");

        // Add our console view to the window
        linearLayout.addView(logView);

        // Set the only view button to kill our application
        Button endMe = (Button) findViewById(R.id.button);
        endMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                example();
            }
        }).start();
    }

    // The goal of this example is to demonstrate that invoking a kernel over a vector of
    // allocations is far worse than invoking it on a single, big one.
    private void example() {

        // Init RenderScript
        final RenderScript mRS = RenderScript.create(this);
        ScriptC_main scriptC_main = new ScriptC_main(mRS);

        // --- Common allocations values
        // This test generates a set of allocations, each having a smaller size.
        // The size is determined by downscaling each allocation by a scale factor
        // that equals allocationScale variable.
        int allocationInitialWidth = 1024 * 768;
        float allocationScale = 1.2f;

        Element allocationElement = Element.F32(mRS);

        // --- Setup of vector allocations
        int allocationsCount = 8;
        int totalAllocationsSize = 0;
        ArrayList<Allocation> vectorAllocations = new ArrayList<>();
        ArrayList<Allocation> outputVectorAllocations = new ArrayList<>();

        for (int i = 0; i < allocationsCount; i++) {
            int allocationSize = (int) ((float) allocationInitialWidth / Math.pow(allocationScale, i));
            vectorAllocations.add(Allocation.createSized(mRS, allocationElement, allocationSize));
            outputVectorAllocations.add(Allocation.createSized(mRS, allocationElement, allocationSize));
            totalAllocationsSize += allocationSize;
            Log.d(TAG, String.format("Created vector allocation with size %d", allocationSize));
        }
        Log.d(TAG, String.format("Total vector allocations size: %d", totalAllocationsSize));

        // --- Setup of single allocation
        // Calculates total allocation size
        int singleAllocationSize = 0;
        for (int i = 0; i < allocationsCount; i++) {
            singleAllocationSize += (int) ((float) allocationInitialWidth / Math.pow(allocationScale, i));
        }
        Allocation singleAllocation = Allocation.createSized(mRS, allocationElement, singleAllocationSize);
        Allocation outputSingleAllocation = Allocation.createSized(mRS, allocationElement, singleAllocationSize);
        Log.d(TAG, String.format("Created single allocation with size %d", singleAllocationSize));

        Log.d(TAG, "Filling allocations with random data");
        // --- Prefill allocations with random data
        scriptC_main.forEach_fillWithRandomData(singleAllocation);
        for (int i = 0; i < allocationsCount; i++) {
            scriptC_main.forEach_fillWithRandomData(vectorAllocations.get(i));
        }

        mRS.finish();

        Log.d(TAG, "Starting example execution");

        // --- Execute example
        final Timings timings = new Timings(this);
        timings.setTimingDebugInterval(10);

        timings.setTimingCallback(new Timings.TimingCallback() {
            @Override
            public void run() {
                mRS.finish();
            }
        });

        while (!timings.isFirstTimingEnded()) {

            timings.initTimings();

            scriptC_main.forEach_compute(singleAllocation, outputSingleAllocation);
            timings.addTiming("Single allocation execution");

            for (int i = 0; i < allocationsCount; i++) {
                scriptC_main.forEach_compute(vectorAllocations.get(i), outputVectorAllocations.get(i));
            }
            timings.addTiming("Vector allocations execution");

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
}
