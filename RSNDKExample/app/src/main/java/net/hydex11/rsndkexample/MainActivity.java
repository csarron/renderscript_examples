package net.hydex11.rsndkexample;

import android.renderscript.RenderScript;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.io.UnsupportedEncodingException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "RSJava";

    LogView logView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize log view
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.linearLayout);

        // Create a view to see LogCat log
        logView = new LogView(this, new String[]{"RSNDK", "RSJava"}, 5);
        logView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        logView.addLogLine("Wait for logs. It is going to take some seconds...\n");

        // Add our console view to the window
        linearLayout.addView(logView);

        new Thread(new Runnable() {
            @Override
            public void run() {
                example();
            }
        }).start();
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

    private void example() {

        System.loadLibrary("RSNDK");

        Log.d(TAG, "Initializing RS context");

        // Initialize RS context
        String cacheDirPath = getCacheDir().getAbsolutePath();

        initRenderScript(cacheDirPath, BuildConfig.DEBUG);

        Log.d(TAG, "Initializing example");
        ndkExample();
    }

    private static native void initRenderScript(String cacheDir, boolean useDebugMode);

    private static native void ndkExample();
}
