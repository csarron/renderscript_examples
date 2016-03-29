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

package net.hydex11.dynamicbitcodeloaderexample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Run example
        example();
    }

    // renderScriptHolder object will be our interfacing with native RenderScript functions.
    RenderScriptHolder renderScriptHolder;

    private void example() {

        // Initialize views, loads input image
        initViews();

        // Runs a new thread as we have a network call to do, that would throw an Exception
        // if run on UI thread
        new Thread(new Runnable() {
            @Override
            public void run() {

                // Our desired remote script name
                String remoteScriptName = "computethreshold";

                // Downloads file and returns zipped file path
                String localFileName = ScriptDownloader.downloadScript(remoteScriptName);

                // Unzips archive
                Unzip.unzipRemoteFile(localFileName, ScriptDownloader.localDownloadDir);

                File scriptFile = new File(ScriptDownloader.localDownloadDir, remoteScriptName + ".bc");
                File propertiesFile = new File(ScriptDownloader.localDownloadDir, remoteScriptName + ".properties");

                // Loads properties file
                Properties properties = new Properties();
                try {
                    InputStream is = new FileInputStream(propertiesFile);
                    properties.load(is);
                    is.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                // Loads a property, in our case "kernelSlot". It not present, crash!
                int kernelSlot;
                try {
                     kernelSlot = Integer.parseInt(properties.getProperty("kernelSlot"));
                }catch (NumberFormatException e){
                    throw new RuntimeException(e);
                }

                // Test this script
                renderScriptHolder = new RenderScriptHolder(MainActivity.this);
                try {
                    long ptr = renderScriptHolder.loadDynamicScript(remoteScriptName, scriptFile);

                    testScript(renderScriptHolder.getRenderScriptContext(), ptr, kernelSlot);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

    // From here on, test!

    Bitmap inputImage;

    // Loads views
    ImageView imageViewInput;
    ImageView imageViewOutput;

    // Initialize views, ran on UI thread
    private void initViews() {
        inputImage = BitmapFactory.decodeResource(getResources(), R.drawable.photo_wide);

        // Loads views
        imageViewInput = (ImageView) findViewById(R.id.imageView);
        imageViewOutput = (ImageView) findViewById(R.id.imageView2);

        imageViewInput.setImageBitmap(inputImage);
    }

    private void testScript(RenderScript mRS, long scriptPtr, int kernelSlot) {
        // Loads input image

        // Fills input allocation with source image
        Allocation inputAllocation = Allocation.createFromBitmap(mRS, inputImage);

        // Builds output allocation
        Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tb.setX(inputImage.getWidth());
        tb.setY(inputImage.getHeight());
        Allocation outputAllocation = Allocation.createTyped(mRS, tb.create());

        // Call kernel! Here is our magic :)
        renderScriptHolder.callKernel(scriptPtr, kernelSlot, inputAllocation, outputAllocation);

        // Displays output image in view
        final Bitmap outputImage = Bitmap.createBitmap(inputImage.getWidth(), inputImage.getHeight(), Bitmap.Config.ARGB_8888);
        outputAllocation.copyTo(outputImage);

        // Runs on UI thread, as only UI thread can update UI components
        Common.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                imageViewOutput.setImageBitmap(outputImage);
            }
        });
    }

}
