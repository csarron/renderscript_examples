package net.hydex11.rsndkexample;

import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RSJava";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    private void example() {

        System.loadLibrary("gnustl_shared");
        System.loadLibrary("RSNDK");

        // Initialize log view
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // Create a view to see LogCat log
        LogView logView = new LogView(this, new String[]{"RSNDK", "RSJava"},5);
        logView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logView.addLogLine("Wait for logs. It is going to take some seconds...\n");

        // Add our console view to the window
        linearLayout.addView(logView);

        Log.d(TAG, "Initializing RS context");

        // Initialize RS context
        String cacheDirPath = getCacheDir().toString();
        initRenderScript(cacheDirPath);

        Log.d(TAG, "Initializing example");
        ndkExample();
    }

    private static native void initRenderScript(String cacheDir); //, int stringLength);
    private static native void ndkExample();
}
