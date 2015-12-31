package net.hydex11.surfacerenderexample;

import android.graphics.SurfaceTexture;
import android.os.AsyncTask;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.Type;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.TextureView;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    static final String TAG = "SurfaceRenderExample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        example();
    }

    private void example() {

        TextureView textureView = (TextureView) findViewById(R.id.textureView);
        initRenderScript();

        textureView.setScaleX(1.00001f);
        textureView.setSurfaceTextureListener(mSurfaceTextureListener);
    }

    TextureView.SurfaceTextureListener mSurfaceTextureListener = new TextureView.SurfaceTextureListener() {
        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture surface, int width, int height) {
            Log.d(TAG, "Preview surface created");

            Surface _surface = new Surface(surface);

            renderAllocation.setSurface(_surface);

            if (!surfaceIsSet) {
                synchronized (this) {
                    surfaceIsSet = true;
                    RSLoop();
                }
            }
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width, int height) {
            /*Log.d(TAG, "Preview surface changed");

            Surface _surface = new Surface(surface);

            renderAllocation.setSurface(_surface);

            if (!surfaceIsSet) {
                synchronized (this) {
                    surfaceIsSet = true;
                    RSLoop(_surface);
                }
            }*/
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

    boolean surfaceIsSet = false;

    final int particlesCount = 500;
    final int renderAllocationWidth = 720/2;
    final int renderAllocationHeight = 1280/2;

    private void initRenderScript() {
        mRS = RenderScript.create(this);

        scriptMain = new ScriptC_main(mRS);

        Type.Builder tb = new Type.Builder(mRS, Element.RGBA_8888(mRS));
        tb.setX(renderAllocationWidth);
        tb.setY(renderAllocationHeight);

        renderAllocation = Allocation.createTyped(mRS, tb.create(), Allocation.USAGE_SCRIPT | Allocation.USAGE_IO_OUTPUT);

        Element particleElement = ScriptField_Particle.createElement(mRS);
        particlesAllocation = Allocation.createSized(mRS, particleElement, particlesCount);

        // Init script vars
        scriptMain.set_renderAllocation(renderAllocation);
        scriptMain.set_particlesAllocation(particlesAllocation);

        scriptMain.set_renderAllocationWidth(renderAllocationWidth);
        scriptMain.set_renderAllocationHeight(renderAllocationHeight);

        // Init particles allocation
        scriptMain.forEach_initParticles(particlesAllocation, particlesAllocation);
    }

    boolean isProcessing = false;

    private void RSLoop() {
        new Thread(new Runnable(){

            @Override
            public void run() {

                boolean canRun=true;
                long count=0;

                while (canRun) {

                    if(!isProcessing) {
                        isProcessing= true;
                        //Log.d(TAG, "New frame");

                        loop();

                        count++;

                    }
                    else
                    {
                        try {
                            Thread.sleep(20,0);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }).start();
    }

    private void loop(){
        scriptMain.forEach_cleanRenderAllocation(renderAllocation);
        scriptMain.forEach_drawParticles(particlesAllocation, particlesAllocation);

        renderAllocation.ioSend();

        mRS.finish();

isProcessing=false;
    }
}
