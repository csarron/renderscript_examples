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

package net.hydex11.customjavaelementexample;

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

    LogView logView;
    void example() {

        logView = new LogView(this, "RenderScript", 5);
        logView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.myLayout);
        relativeLayout.addView(logView);

        // Instantiates new RS Context
        RenderScript mRS = RenderScript.create(this);

        // Instantiates main script
        ScriptC_main main = new ScriptC_main(mRS);

        Element.Builder eb = new Element.Builder(mRS);
        eb.add(Element.I32(mRS), "x");
        eb.add(Element.I32(mRS), "y");
        eb.add(Element.F32(mRS), "fx");
        eb.add(Element.F32(mRS), "fy");

        // Define my element
        Element myElement = eb.create();

        int elementsCount = 5;
        Allocation mAlloc = Allocation.createSized(mRS, myElement, elementsCount);
        main.set_aIn(mAlloc);

        // Creates an Allocation that will contain indices
        int indexes[] = new int[elementsCount];
        for (int i = 0; i < elementsCount; i++)
            indexes[i] = i; // So that indexes[0] = 0, indexes[1] = 1 etc..
        Allocation mIndexesAllocation = Allocation.createSized(mRS, Element.I32(mRS), elementsCount);

        // Fills indexes Allocation with calculated indexes
        mIndexesAllocation.copyFrom(indexes);

        // Fills the custom allocation with some data
        main.forEach_initializeMyElements(mIndexesAllocation);

        main.forEach_debugAllocation(mIndexesAllocation);
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
}
