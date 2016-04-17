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

import android.os.Build;
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load our NDK create library
        System.loadLibrary("native");

        // Starts our example
        executeKernel();
    }

    // Function used to retrieve the allocation pointer. We need to inspect
    // this class private element mID using reflection.
    // getSuperclass() is used because mID is actually declared in BaseObj class,
    // that Allocation class extends from.
    long getAllocationPointer(Allocation allocation) {
        Field allocationIDField = null;
        long AllocationID = 0;
        try {
            allocationIDField = allocation.getClass().getSuperclass().getDeclaredField("mID");
            // Sets field accessible by our code
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

    void executeKernel() {

        TextView textView2 = (TextView) findViewById(R.id.textView2);
        TextView textViewJava = (TextView) findViewById(R.id.textViewJava);
        TextView textViewNDK = (TextView) findViewById(R.id.textViewNDK);
        TextView textViewNativeForEach = (TextView) findViewById(R.id.textViewNativeForEach);

        // This kernel will sum 1 to every element of the input allocation and store the result in the output one

        // Creates RenderScript context
        RenderScript mRS = RenderScript.create(this);

        // Initializes sum script
        ScriptC_sum sum = new ScriptC_sum(mRS);

        // Creates some values to use as source and prints them
        byte sourceArray[] = {15, 30, 45};
        // Prints values, expected 10, 30, 45
        printOutValues(textView2, sourceArray, true);

        // Defines input and output allocations
        Allocation inAllocation = Allocation.createSized(mRS, Element.U8(mRS), sourceArray.length);
        Allocation outAllocation = Allocation.createSized(mRS, Element.U8(mRS), sourceArray.length);

        // Copies predefined values to the input allocation
        inAllocation.copyFrom(sourceArray);

        // Computes the kernel
        sum.forEach_sum1(inAllocation, outAllocation);

        // It is needed to call the finish function before accessing the Allocation natively, as
        // kernels are not synchronous to Java but only to RS context.
        mRS.finish();

        byte outArray[] = new byte[sourceArray.length];

        // Copies output to local variable (for demonstration purposes)
        outAllocation.copyTo(outArray);
        // Prints values, expected 11, 31, 46
        printOutValues(textViewJava, outArray, true);

        // Retrieves output allocation address
        long allocationPtr = getAllocationPointer(outAllocation);
        long ContextID = getContextPointer(mRS);

        // Retrieves result using native way
        int nativeResult = executeNativeExtraction(ContextID, allocationPtr);

        // Unpacks native result and displays it
        byte nativeOutArray[] = new byte[3];
        nativeOutArray[0] = (byte) (nativeResult & 0xff);
        nativeOutArray[1] = (byte) ((nativeResult & 0xff00) >> 8);
        nativeOutArray[2] = (byte) ((nativeResult & 0xff0000) >> 16);

        // Prints values, expected 11, 31, 46
        printOutValues(textViewNDK, nativeOutArray,false);

        // --- Native kernel call test
        // Retrieves all addresses of required elements
        long AllocationInID = getAllocationPointer(inAllocation);
        long AllocationOutID = getAllocationPointer(outAllocation);
        long ScriptID = getScriptPointer(sum);

        // Android API version lower than 19 have a different
        // symbol mapping inside the libRS.so library
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT){
            textViewNativeForEach.setText("Native kernel call not supported");
            return;
        }

        // Calls the forEach from native NDK side. Called kernel is sum2,
        // which will sum 5 to every element of the input allocation and store the result in the output one
        if(!executeNativeKernel(ContextID, ScriptID, AllocationInID, AllocationOutID))
            // If the runtime library doesn't support something,
            // return and do not execute this section
            return;

        // Debug output
        outAllocation.copyTo(outArray);
        // Prints values, expected 15, 35, 50
        printOutValues(textViewNativeForEach, outArray, false);

    }

    native int executeNativeExtraction(long ContextID, long AllocationID);
    native boolean executeNativeKernel(long ContextID, long ScriptID, long AllocationInID, long AllocationOutID);

    long getContextPointer(RenderScript rsContext) {
        Field contextIDField = null;
        long ContextID = 0;
        try {
            contextIDField = rsContext.getClass().getDeclaredField("mContext");
            contextIDField.setAccessible(true);
            ContextID = (long) contextIDField.getLong(rsContext);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access Context ID");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find Context ID");
        }

        if (ContextID == 0) {
            throw new RuntimeException("Invalid rsContext ID");
        }
        return ContextID;
    }

    // Here we use getSuperclass() three times, as the script class will be a auto-generated one,
    // that extends ScriptC one, that extends Script one, that extends BaseObj one.
    long getScriptPointer(ScriptC scriptC) {
        Field scriptCIDField = null;
        long scriptCID = 0;
        try {
            scriptCIDField = scriptC.getClass().getSuperclass().getSuperclass().getSuperclass().getDeclaredField("mID");
            scriptCIDField.setAccessible(true);
            scriptCID = (long) scriptCIDField.getLong(scriptC);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access ScriptC ID");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find ScriptC ID");
        }

        if (scriptCID == 0) {
            throw new RuntimeException("Invalid ScriptC ID");
        }
        return scriptCID;
    }

    // Function to debug our values to a TextView
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
}
