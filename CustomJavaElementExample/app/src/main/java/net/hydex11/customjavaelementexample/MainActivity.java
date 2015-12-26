package net.hydex11.customjavaelementexample;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.StreamCorruptedException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    void example() {
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
}
