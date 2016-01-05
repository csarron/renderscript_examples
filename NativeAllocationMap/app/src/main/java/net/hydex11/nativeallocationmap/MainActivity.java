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

package net.hydex11.nativeallocationmap;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("native");

        executeKernel();
    }

    long getAllocationPointer(Allocation allocation) {
        Field allocationIDField = null;
        long AllocationID = 0;
        try {
            allocationIDField = allocation.getClass().getSuperclass().getDeclaredField("mID");
            allocationIDField.setAccessible(true);
            AllocationID = (long) allocationIDField.getLong(allocation);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access Allocation ID");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find Allocation ID");
        }

        if (AllocationID == 0) {
            throw new RuntimeException("Invalid allocation ID");
        }
        return AllocationID;
    }

    void printOutValues(TextView outTextView, byte[] values, boolean isJava) {
        String out = "Values, ";

        if (isJava)
            out += "Java: ";
        else
            out += "NDK: ";

        for (byte b : values) {
            out += (int) b + ", ";
        }

        outTextView.setText(out);
    }

    void executeKernel() {

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textViewJava = (TextView) findViewById(R.id.textViewJava);
        TextView textViewNDK = (TextView) findViewById(R.id.textViewNDK);

        // This kernel will sum 1 to every element of the input allocation and store the result in the output one

        // Creates RenderScript context
        RenderScript mRS = RenderScript.create(this);

        // Initializes sum script
        ScriptC_sum sum = new ScriptC_sum(mRS);

        // Creates some values to use as source and prints them
        byte sourceArray[] = {10, 30, 45};
        printOutValues(textView2, sourceArray, true);

        // Defines input and output allocations
        Allocation inAllocation = Allocation.createSized(mRS, Element.I32(mRS), 3);
        Allocation outAllocation = Allocation.createSized(mRS, Element.U8(mRS), 3);

        // Copies predefined values to the input allocation
        inAllocation.copyFrom(sourceArray);

        // Computes the kernel
        sum.forEach_sum1(inAllocation, outAllocation);

        // It is needed to call the finish function before accessing the Allocation natively, as
        // kernels are not synchronous to Java but only to RS context.
        mRS.finish();

        byte outArray[] = new byte[3];

        // Copies output to local variable (for demonstration purposes)
        outAllocation.copyTo(outArray);
        printOutValues(textViewJava, outArray, true);

        // Retrieves output allocation address
        long allocationPtr = getAllocationPointer(outAllocation);

        // Retrieves result using native way
        int nativeResult = executeNativeExtraction(allocationPtr);

        // Unpacks native result and displays it
        byte nativeOutArray[] = new byte[3];
        nativeOutArray[0] = (byte) (nativeResult & 0xff);
        nativeOutArray[1] = (byte) ((nativeResult & 0xff00) >> 8);
        nativeOutArray[2] = (byte) ((nativeResult & 0xff0000) >> 16);

        printOutValues(textViewNDK, nativeOutArray,false);
    }

    native int executeNativeExtraction(long allocationPtr);
}
