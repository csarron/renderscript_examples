package net.hydex11.profilerexample;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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

        // Set the only view button to kill our application
        Button endMe = (Button) findViewById(R.id.button);
        endMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (exampleThread != null)
                    // We force the interruption of loop thread (may cause an exception,
                    // but this is just a RS example!
                    exampleThread.interrupt();

                MainActivity.this.finish();
            }
        });

        // As we are going over a loop, it is needed to not run it on UI thread, as we'd
        // get frozen window rendering. So, just make another one.
        exampleThread = new Thread(new Runnable() {
            @Override
            public void run() {

                // Instantiate our RS context
                final RenderScript mRS = RenderScript.create(MainActivity.this);

                int count = 100000;

                // Source of our Allocation, just some numbers
                int myBigArray[] = new int[count];
                for (int i = 0; i < count; i++) {
                    myBigArray[i] = count;
                }

                // Instantiates the allocation. Be sure to not use a too big count, as it could
                // cause an Out of Memory error
                Allocation myAllocation = Allocation.createSized(mRS, Element.I32(mRS), count);
                myAllocation.copyFrom(myBigArray);

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

                // We create two different scripts, that has same kernels. First one is
                // standard RenderScript, second one uses FilterScript approach. This way
                // you can see differences in performance (please use an high end device to
                // completely notice the difference).
                ScriptC_main main = new ScriptC_main(mRS);
                ScriptC_main_fs main_fs = new ScriptC_main_fs(mRS);

                // Tells the profiler to output debug data every 100 cycles
                timings.setTimingDebugInterval(100);

                // My loop
                while (true) {
                    // Calling this function, the profiler sets current time as initial one
                    timings.initTimings();

                    main.forEach_root1(myAllocation, myAllocation);
                    // Adds timing for kernel
                    timings.addTiming("root");

                    main.forEach_root2(myAllocation, myAllocation);
                    timings.addTiming("root2");

                    main.forEach_root3(myAllocation, myAllocation);
                    timings.addTiming("root3");

                    main_fs.forEach_root1(myAllocation, myAllocation);
                    // Adds timing for kernel
                    timings.addTiming("root - fs");

                    main_fs.forEach_root2(myAllocation, myAllocation);
                    timings.addTiming("root2 - fs");

                    main_fs.forEach_root3(myAllocation, myAllocation);
                    timings.addTiming("root3 - fs");

                    // Checks if this cycle is the correct one for debugging timings and outputs them
                    // in case it is.
                    timings.debugTimings();

                    try {
                        // Small wait, to not overkill the CPU/GPU
                        Thread.sleep(10,0);
                    } catch (InterruptedException e) {
                        // Will be caused by clicking on "End" button, as we will be interrupting
                        // this Thread brutally
                        throw new RuntimeException(e);
                    }
                }
            }
        });
        exampleThread.start();
    }
}
