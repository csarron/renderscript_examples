package net.hydex11.rsndkexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    private void example() {

        System.loadLibrary("RSNDK");

        // Initialize RS context
        initRenderScript(getCacheDir().getAbsolutePath());

    }

    private static native void initRenderScript(String cacheDir);
    private static native void ndkExample();
}
