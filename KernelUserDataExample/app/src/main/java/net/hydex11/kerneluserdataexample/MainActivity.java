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

package net.hydex11.kerneluserdataexample;

// In this example we are not using Android support library as we are using reflection methods,
// specific for android.renderscript package.
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "KernelUserDataExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializes example
        example();
    }

    LogView logView;
    private void example() {
        // Add custom automated filter logging view
        logView = new LogView(this, TAG, 5);
        logView.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
        relativeLayout.addView(logView);

        // Instantiates RS context and our script
        RenderScript mRS = RenderScript.create(this);
        ScriptC_main myScript = new ScriptC_main(mRS);

        // Creates input sample data
        int inputLength = 10;

        int addValue = 3;
        int mulValue = 4;

        // Sample data
        int inputArray[] = new int[inputLength];
        for(int i = 0; i < inputArray.length; i++)
            inputArray[i] = i;
        Log.d(TAG, "Input data: " + arrayToString(inputArray));

        // Creates input allocation and copies sample data
        Allocation allocationInput = Allocation.createSized(mRS, Element.I32(mRS), inputLength);
        allocationInput.copyFrom(inputArray);

        // Creates output allocation and sets it into script
        Allocation allocationOutput = Allocation.createSized(mRS, Element.I32(mRS), inputLength);
        myScript.set_allocationOutput(allocationOutput);

        // Calls invoking function, with custom addValue and mulValue (refer to rs script)
        myScript.invoke_invokeCalculation(myScript, allocationInput, addValue, mulValue);

        // Debugs output data
        int outputArray[] = new int[inputLength];
        allocationOutput.copyTo(outputArray);

        Log.d(TAG, "Output data: " + arrayToString(outputArray));

        // Passes user data using reflection:
        // Automatically generated RenderScript code does not let users
        // pass custom data to kernels. However, support for this action
        // exists and can be toggled with Java reflection.

        // Initializes a FieldPacker that will embed our custom values
        int sizeOfFieldPacker = 2 * Integer.SIZE;

        FieldPacker fieldPacker = new FieldPacker(sizeOfFieldPacker);
        fieldPacker.addI32(addValue);
        fieldPacker.addI32(mulValue);

        // kernelSlot had to be 0, as we are using a kernel named root
        int kernelSlot = 0;

        // Retrieves hidden forEach method using reflection and invokes it
        Method forEachMethod = getForEachMethod();
        try {
            forEachMethod.invoke(myScript, kernelSlot, allocationInput, null, fieldPacker);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Debugs output values (should equal previous debug output)
        allocationOutput.copyTo(outputArray);

        Log.d(TAG, "Output data (Reflection): " + arrayToString(outputArray));
    }

    // Retrieves protected forEach method
    private Method getForEachMethod(){
        // protected void forEach(int slot, Allocation ain, Allocation aout, FieldPacker v)
        Method forEachMethod;

        try {
            forEachMethod = Script.class.getDeclaredMethod("forEach", int.class, Allocation.class, Allocation.class, FieldPacker.class);
            forEachMethod.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("forEach method doesn't exist");
        }

        return forEachMethod;
    }

    // Converts int array to string
    private String arrayToString(int inputArray[]){
        String out = "";

        for(int element: inputArray)
            out+= element + ", ";

        return out;
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
