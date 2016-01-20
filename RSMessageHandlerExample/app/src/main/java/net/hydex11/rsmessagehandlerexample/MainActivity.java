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

package net.hydex11.rsmessagehandlerexample;

import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MessageHandlerExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    static final int MESSAGE_OK = 1;

    RenderScript.RSMessageHandler myHandler = new RenderScript.RSMessageHandler() {
        @Override
        public void run() {
            switch (mID) {
                case ScriptC_main.const_MESSAGE_OK: {
                    // Handle mData, which is a int array
                    int x = mData[0];
                    int y = mData[1];
                    boolean myBool = mData[2] == 1;

                    // Notice usage of intBitsToFloat to cast integer to float
                    float f = Float.intBitsToFloat(mData[3]);

                    Log.d(TAG, String.format("x: %d, y: %d, bool: %d, float: %f", x, y, myBool ? 1 : 0, f));
                }
                break;
                default:
                    super.run();
                    break;
            }
        }
    };

    void example() {


        // Add custom automated filter logging view
        LogView logView = new LogView(this, TAG);
        logView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.addView(logView);

        RenderScript mRS = RenderScript.create(this);
        mRS.setMessageHandler(myHandler);

        ScriptC_main main = new ScriptC_main(mRS);

        main.invoke_callMe();

    }
}
