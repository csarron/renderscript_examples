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

        example();
    }

    Thread exampleThread;
    private void example() {

        Button endMe = (Button) findViewById(R.id.button);
        endMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(exampleThread!=null)
                    exampleThread.interrupt();

                MainActivity.this.finish();
            }
        });

        exampleThread = new Thread(new Runnable() {
            @Override
            public void run() {


                final RenderScript mRS = RenderScript.create(MainActivity.this);

                int count = 100000;

                int myBigArray[] = new int[count];
                for (int i = 0; i < count; i++) {
                    myBigArray[i] = count;
                }

                Allocation myAllocation = Allocation.createSized(mRS, Element.I32(mRS), count);
                myAllocation.copyFrom(myBigArray);

                Timings timings = new Timings();
                timings.setTimingCallback(new Timings.TimingCallback() {
                    @Override
                    public void run() {
                        mRS.finish();
                    }
                });

                ScriptC_main main = new ScriptC_main(mRS);

                timings.setTimingDebugInterval(100);

                // My loop
                while (true) {

                    timings.initTimings();

                    main.forEach_root1(myAllocation, myAllocation);
                    timings.addTiming("root");

                    main.forEach_root2(myAllocation, myAllocation);
                    timings.addTiming("root2");

                    main.forEach_root3(myAllocation, myAllocation);
                    timings.addTiming("root3");

                    timings.debugTimings();

                    try {
                        Thread.sleep(10);
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
