package net.hydex11.rsmessagehandlerexample;

import android.renderscript.RenderScript;
import android.renderscript.ScriptC;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    example();
    }

    static final int MESSAGE_OK = 1;

    RenderScript.RSMessageHandler myHandler = new RenderScript.RSMessageHandler(){
        @Override
        public void run()
        {
            switch (mID)
            {
                case ScriptC_main.const_MESSAGE_OK:
                {
                    // Handle mData, which is a int array
                    int x = mData[0];
                    int y = mData[1];
                    boolean myBool = mData[2] == 1;

                    // Notice usage of intBitsToFloat to cast integer to float
                    float f = Float.intBitsToFloat(mData[3]);

                    TextView textView = (TextView) findViewById(R.id.textView);

                    String outStr = String.format("x: %d, y: %d, bool: %d, float: %f", x,y,myBool ? 1 : 0, f);
                    textView.setText(outStr);
                }
                break;
                default: super.run();
                    break;
            }
        }
    };

    void example(){

        RenderScript mRS = RenderScript.create(this);
        mRS.setMessageHandler(myHandler);

        ScriptC_main main = new ScriptC_main(mRS);

        main.invoke_callMe();

    }
}
