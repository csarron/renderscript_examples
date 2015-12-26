package net.hydex11.customelementexample;

import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    void example(){

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
}
