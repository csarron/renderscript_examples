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

package net.hydex11.customelementexample;

import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    boolean exampleRan = false;
    LogView logView;
    void example(){

        if(exampleRan)return;
        exampleRan=true;

        // Add custom automated filter logging view
        logView = new LogView(this, new String[]{"RenderScript"});
        logView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.myLayout);
        relativeLayout.addView(logView);

        // Instantiates new RS Context
        RenderScript mRS = RenderScript.create(this);

        // Instantiates main script
        ScriptC_main main = new ScriptC_main(mRS);

        // Declares a new Allocation, having our custom struct Element as base
        Element myElement = ScriptField_MyElement.createElement(mRS);
        Allocation myElementsAllocation = Allocation.createSized(mRS, myElement, 5);

        // Fills the Allocation with some data and debugs it
        main.forEach_initializeMyElements(myElementsAllocation);
        main.forEach_debugAllocation(myElementsAllocation);

        // Debug is visible inside logcat under "My custom Element" string
    }

    @Override
    protected void onDestroy() {
        if(logView != null)
            logView.destroy();

        super.onDestroy();
    }
}
