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

package net.hydex11.surfacerenderexample;

import android.graphics.SurfaceTexture;

//import android.renderscript.*;
import android.support.v8.renderscript.*;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;

/*
* SurfaceRenderExample shows how to use a surface (in this case a TextureView) to display
* a RenderScript processing output.
*
* Here, we create a custom Particle struct, representing a general particle subject to
* vertical screen-axis acceleration.
*
* */
public class MainActivity extends AppCompatActivity {

    static final String TAG = "SurfaceRenderExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the example
        example();
    }

    // Thread that handles the compute loop
    Thread rsLoop;

    private void example() {
        // Initializes RenderScript context
        initRenderScript();

        // Here we get our TextureView, and apply a hack (setScaleX) to enable smoothing of the
        // final output image
        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        textureView.setScaleX(1.00001f);

        // Sets surface callback, to understand when the surface will be available
        textureView.setSurfaceTextureListener(mSurfaceTextureListener);

        textureView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // After touching the surface, application will end.
                if(rsLoop != null)
                    rsLoop.interrupt();

                System.exit(0);
            }
        });
    }

    TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.d(TAG, "Preview surface created");

            // Once the surface is initialized, we can tell output Allocation to use it
            renderAllocation.setSurface(new Surface(surface));

            // Initializes the processing endless loop
            RSLoop();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
            return false;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture surface) {

        }
    };

    // RenderScript
    RenderScript mRS;
    ScriptC_main scriptMain;
    Allocation renderAllocation;
    Allocation particlesAllocation;

    // Our rendering settings
    final int particlesCount = 500;
    final int renderAllocationWidth = 720 / 2; // Image that spans more vertically (portrait) than horizontally.
    final int renderAllocationHeight = 1280 / 2;

    // Note: output allocation should not have really big resolution, as RenderScript itself
    // is not really made for real time graphics (that we want to achieve here).
    // If, for other applications, you can accept a delay, you can then use larger resolutions.

    private void initRenderScript() {
        mRS = RenderScript.create(this);

        scriptMain = new ScriptC_main(mRS);

        // Defines output allocation type. As we are using a surface, we need to impose RGBA_8888 element (equal to U8_4)
        Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tb.setX(renderAllocationWidth);
        tb.setY(renderAllocationHeight);
        // We instantiate the output allocation, setting USAGE_IO_OUTPUT as it will give its data to the surface
        renderAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

        // Here we define out custom Particle element allocation
        Element particleElement = ScriptField_Particle.createElement(mRS);
        particlesAllocation = Allocation.createSized(mRS, particleElement, particlesCount);

        // Init script vars
        scriptMain.set_renderAllocation(renderAllocation);
        scriptMain.set_particlesAllocation(particlesAllocation);

        scriptMain.set_renderAllocationWidth(renderAllocationWidth);
        scriptMain.set_renderAllocationHeight(renderAllocationHeight);

        // Init particles allocation, to be sure that all particles are not valid at the beginning.
        // To understand the "valid" word, please look the script's code.
        scriptMain.forEach_initParticles(particlesAllocation, particlesAllocation);
    }

    // Boolean used to determine when there is a calculation in progress. This way, we can prevent
    // the system to get stressed. Whenever calculation is in progress, we wait 20 milliseconds for it
    // to finish, relaxing the CPU.
    boolean isProcessing = false;

    private void RSLoop() {
        // As we are moving on an endless loop, it is better to not run it on the UI thread, but
        // on a different one
        rsLoop = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {

                    if (!isProcessing) {
                        isProcessing = true;

                        // Calls calculation function
                        loop();

                    } else {

                        // Waits for current calculation to end, by sleeping
                        try {
                            Thread.sleep(20, 0);
                        } catch (InterruptedException e) {
                            // Can be caused by touching the surface, as we will be ending this
                            // Thread in a bad manner
                           Log.d(TAG, "Thread interrupted");
                        }
                    }
                }

            }
        });
        rsLoop.start();
    }

    private void loop() {

        // Clears output allocation by setting every pixel to black
        scriptMain.forEach_cleanRenderAllocation(renderAllocation);

        // Updates particles and draws their current state
        scriptMain.forEach_drawParticles(particlesAllocation, particlesAllocation);

        // Waits for all kernels to finish (can be not necessary if working ONLY inside RS context)
        //mRS.finish();

        // Sends output allocation to surface
        renderAllocation.ioSend();

        // Tells the environment that a new calculation can start
        isProcessing = false;
    }
}
