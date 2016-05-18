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

package net.hydex11.rsndkexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/*
 * ----- IMPORTANT NOTICE!! -----
 * To be able to run this sample project, you must configure it to use
 * a NDK that supports RenderScript native NDK side, like the 10e
 */

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RSJava";

    LogView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize log view
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // Create a view to see LogCat log
        logView = new LogView(this, new String[]{"RSNDK", "RSJava"}, 5);
        logView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logView.addLogLine("Wait for logs. It is going to take some seconds...\n");

        // Add our console view to the window
        linearLayout.addView(logView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                example();
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(logView!= null)
        {
            logView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(logView!= null)
        {
            logView.onPause();
        }
    }

    private void example() {

        System.loadLibrary("RSNDK");

        Log.d(TAG, "Initializing RS context");

        // Initialize RS context
        String cacheDirPath = getCacheDir().getAbsolutePath();

        initRenderScript(cacheDirPath, BuildConfig.DEBUG);

        Log.d(TAG, "Initializing example");
        ndkExample();
    }

    private static native void initRenderScript(String cacheDir, boolean useDebugMode);

    private static native void ndkExample();
}
