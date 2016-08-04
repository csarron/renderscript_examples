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

package net.hydex11.opencvinteropexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v8.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import org.opencv.android.Utils;
import org.opencv.core.Mat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/*
What this sample project does is:

1. Loads a custom drawable file, bundled together with the app, inside a Bitmap.
2. Creates an OpenCV mat from the Bitmap.
3. Instantiates a RenderScript allocation, which points to the OpenCV mat data pointer.
4. Instantiates another RS allocation, which points to another OpenCV mat, to be used as output.
5. Executes a kernel over the input allocation.
6. Converts the OpenCV output allocation to a Bitmap. **Note:** the output allocation is DIRECTLY
bound to the output OpenCV allocation because they share the same memory address.

Note: please, refer to the `README.md`, which comes together with the example, to have it work
correctly. It requires you to download the OpenCV SDK.

 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        System.loadLibrary("opencv_java3");

        example();
    }

    /*
    The concept of this example is based upon the RS ability to instantiate an Allocation using
    a user-provided data pointer.

    The ability to support a user-provided data pointer is dependent upon the RS device-specific
    driver. When tested on a Galaxy Note 3 (Android 5.1), it didn't work.
    There is a high chance that later versions of Android provide good support for this functionality.

    This functionality works by enabling the RS support library at the cost of using a
    non-performant RS driver.

    ---

    The process of binding a RS allocation to a user-provided pointer can be achieved by directly
    calling the native RS function `RenderScript.nAllocationCreateTyped` and passing it, as last
    argument, the pointer address to the user data (in this case, the OpenCV mat data pointer).

    This process, however, requires the usage of Java reflection:

    1) Retrieve the RenderScript.nAllocationCreateTyped hidden method.
    2) Invoke it to obtain an Allocation pointer.
    3) Instantiate a new Allocation class, using the hidden Allocation() constructor.

     */
    private void example() {
        RenderScript mRS = RenderScript.create(this);

        // Loads input image
        Bitmap inputBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.houseimage);

        // Puts input image inside an OpenCV mat
        Mat inputMat = new Mat();
        Utils.bitmapToMat(inputBitmap, inputMat);

        Mat outputMat = new Mat(inputMat.size(), inputMat.type());

        // Testing bitmap, used to test that the OpenCV mat actually has bitmap data inside
        Bitmap initialBitmap = Bitmap.createBitmap(inputMat.width(), inputMat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(inputMat, initialBitmap);

        // Retrieve OpenCV mat data address
        long inputMatDataAddress = inputMat.dataAddr();
        long outputMatDataAddress = outputMat.dataAddr();

        // Creates a RS type that matches the input mat one.
        Element element = Element.RGBA_8888(mRS);
        Type.Builder tb = new Type.Builder(mRS, element);
        tb.setX(inputMat.width());
        tb.setY(inputMat.height());

        Type inputMatType = tb.create();

        // Creates a RenderScript allocation that uses directly the OpenCV input mat address
        Allocation inputAllocation = createTypedAllocationWithDataPointer(mRS, inputMatType, inputMatDataAddress);
        Allocation outputAllocation = createTypedAllocationWithDataPointer(mRS, inputMatType, outputMatDataAddress);

        // Define a simple convolve script
        // Note: here, ANY kernel can be applied!
        ScriptIntrinsicConvolve3x3 convolve3x3 = ScriptIntrinsicConvolve3x3.create(mRS, element);

        float convolveCoefficients[] = new float[9];
        convolveCoefficients[0] = 1;
        convolveCoefficients[2] = 1;
        convolveCoefficients[5] = 1;
        convolveCoefficients[6] = 1;
        convolveCoefficients[8] = 1;
        convolve3x3.setCoefficients(convolveCoefficients);

        convolve3x3.setInput(inputAllocation);
        convolve3x3.forEach(outputAllocation);

        mRS.finish();

        // Converts the result to a bitmap
        Bitmap cvOutputBitmap = Bitmap.createBitmap(outputMat.width(), outputMat.height(), Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(outputMat, cvOutputBitmap);

        // Testing bitmap, used to test the RenderScript ouput allocation contents
        // Note: it is placed here because the copyTo function clears the input buffer
        Bitmap rsOutputBitmap = Bitmap.createBitmap(outputMat.width(), outputMat.height(), Bitmap.Config.ARGB_8888);
        outputAllocation.copyTo(rsOutputBitmap);

        // Testing bitmap, used to test that RenderScript input allocation pointed to the OpenCV mat
        // Note: it is placed here because the copyTo function clears the input buffer
        Bitmap rsInitialBitmap = Bitmap.createBitmap(inputMat.width(), inputMat.height(), Bitmap.Config.ARGB_8888);
        inputAllocation.copyTo(rsInitialBitmap);

        // Display input and output
        ImageView originalImageIV = (ImageView) findViewById(R.id.imageView);
        ImageView inputRSImageIV = (ImageView) findViewById(R.id.imageView2);
        ImageView outputRSImageIV = (ImageView) findViewById(R.id.imageView3);
        ImageView outputCVIV = (ImageView) findViewById(R.id.imageView4);

        originalImageIV.setImageBitmap(initialBitmap);
        inputRSImageIV.setImageBitmap(rsInitialBitmap);
        outputRSImageIV.setImageBitmap(rsOutputBitmap);
        outputCVIV.setImageBitmap(cvOutputBitmap);

    }

    // Reflection side, used to create a custom allocation that targets a certain data pointer

    // Uses reflection to extract the method used to create a RS allocation in the C++ side.
    //
    // synchronized long nAllocationCreateTyped(long type, int mip, int usage, long pointer)
    //
    Method getnAllocationCreateTyped() {
        Method nAllocationCreateTyped = null;
        try {
            nAllocationCreateTyped = RenderScript.class.
                    getDeclaredMethod("nAllocationCreateTyped", long.class, int.class, int.class, long.class);
            nAllocationCreateTyped.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not access nAllocationCreateTyped method");
        }

        return nAllocationCreateTyped;
    }

    // Get Allocation private constructor
    Constructor<Allocation> getAllocationConstructor() {
        // Allocation(long id, RenderScript rs, Type t, int usage)
        Constructor<Allocation> constructor;

        try {
            constructor = Allocation.class.getDeclaredConstructor(
                    long.class, RenderScript.class, Type.class, int.class
            );
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException("Could not access nAllocationCreateTyped method");
        }

        return constructor;
    }

    // Gets a RenderScript type native pointer
    long getTypePointer(Type rsType) {
        Field typeIDField = null;
        long TypeID = 0;
        try {
            typeIDField = rsType.getClass().getSuperclass().getDeclaredField("mID");
            typeIDField.setAccessible(true);
            TypeID = (long) typeIDField.getLong(rsType);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access Type ID");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find Type ID");
        }

        if (TypeID == 0) {
            throw new RuntimeException("Invalid rsType ID");
        }
        return TypeID;
    }

    // Mimic the Allocation.createTypedAllocationWithDataPointer method
    public Allocation createTypedAllocationWithDataPointer(RenderScript mRS, Type type, long dataPtr) {
        Allocation allocation = null;

        try {

            long typePointer = getTypePointer(type);

            int usage = Allocation.USAGE_SCRIPT | Allocation.USAGE_SHARED;

            Object idObj = getnAllocationCreateTyped().invoke(mRS, typePointer, 0, usage, dataPtr);

            long id = (long) idObj;

            if (id == 0) {
                throw new RSRuntimeException("Allocation creation failed.");
            }

            // Allocation(long id, RenderScript rs, Type t, int usage)
            allocation = getAllocationConstructor().newInstance(id, mRS, type, usage);

        } catch (InvocationTargetException e) {
            throw new RuntimeException("Could not create new Allocation");
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access Allocation constructor");
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not instantiate new Allocation");
        }
        return allocation;
    }
}
