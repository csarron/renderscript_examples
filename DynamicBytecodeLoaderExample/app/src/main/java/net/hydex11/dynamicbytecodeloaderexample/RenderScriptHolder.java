/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2016 - Alberto Marchetti
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

package net.hydex11.dynamicbytecodeloaderexample;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.renderscript.Allocation;
import android.renderscript.RenderScript;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by Alberto on 03/01/2016.
 */
public class RenderScriptHolder {

    // We use external storage to handle RS caching, as we are using custom runtime script and
    // it would not be suitable to interfere with other scripts
    private String mCachePath;

    private Activity context;

    private RenderScript mRS;

    private Method nScriptCCreate;

    public RenderScriptHolder(Activity activity) {
        context = activity;
        mRS = RenderScript.create(context);

        getRSCacheDir();
    }

    public RenderScript getRenderScriptContext() {
        return mRS;
    }

    public long loadDynamicScript(String scriptName, File scriptFile) throws IOException {

        Method initMethod = getRSScriptCCreateMethod();
        String cachePath = getRSCacheDir();

        ScriptData scriptData = readScriptFile(scriptFile);

        // synchronized long nScriptCCreate(String resName, String cacheDir, byte[] script, int length)
        try {
            return (long) initMethod.invoke(mRS, scriptName, cachePath, scriptData.data, scriptData.length);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private class ScriptData {
        private byte[] data;

        public int getLength() {
            return length;
        }

        public byte[] getData() {
            return data;
        }

        private int length;

        public ScriptData(byte[] data, int length) {
            this.data = data;
            this.length = length;
        }
    }

    private ScriptData readScriptFile(File scriptFile) throws IOException {
        byte[] pgm;
        int pgmLength;
        InputStream is = new FileInputStream(scriptFile);

        try {
            pgm = new byte[1024];
            pgmLength = 0;
            while (true) {
                int bytesLeft = pgm.length - pgmLength;
                if (bytesLeft == 0) {
                    byte[] buf2 = new byte[pgm.length * 2];
                    System.arraycopy(pgm, 0, buf2, 0, pgm.length);
                    pgm = buf2;
                    bytesLeft = pgm.length - pgmLength;
                }
                int bytesRead = is.read(pgm, pgmLength, bytesLeft);
                if (bytesRead <= 0) {
                    break;
                }
                pgmLength += bytesRead;
            }
        } finally {
            is.close();
        }

        return new ScriptData(pgm, pgmLength);
    }

    private Method getRSScriptCCreateMethod() {
        if (nScriptCCreate == null)

            try {
                Method method = RenderScript.class.getDeclaredMethod("nScriptCCreate", String.class, String.class, byte[].class, int.class);
                method.setAccessible(true);

                nScriptCCreate = method;

                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("nScriptCCreate method does not exists");
            }

        return nScriptCCreate;
    }

    private String getRSCacheDir() {
        if (mCachePath == null) {
            File extDir = new File(Common.externalDirPath, "cache");

            mCachePath = extDir.getAbsolutePath();

            extDir.mkdirs();
        }

        return mCachePath;
        //        Log.v(TAG, "Create script for resource = " + resName);
        //return rs.nScriptCCreate(resName, mCachePath, pgm, pgmLength);
    }

    public void callKernel(long scriptPointer, int kernelSlot, Allocation ain, Allocation aout) {
        Method initMethod = getMethodnScriptForEach();

        long in[] = new long[1];
        in[0] = getAllocationPointer(ain);
        long out = getAllocationPointer(aout);

        try {
            switch (Build.VERSION.SDK_INT) {
                case Build.VERSION_CODES.LOLLIPOP:
                case Build.VERSION_CODES.LOLLIPOP_MR1:
                    // synchronized void nScriptForEach(long id, int slot, long ain, long aout, byte[] params) {
                    initMethod.invoke(mRS, scriptPointer, kernelSlot, in[0], out, null);
                    break;
                case Build.VERSION_CODES.M:
                    // void nScriptForEach(long id, int slot, long[] ains, long aout, byte[] params, int[] limits)
                    initMethod.invoke(mRS, scriptPointer, kernelSlot, in, out, null, null);
                    break;
                case Build.VERSION_CODES.KITKAT:
                default:
                    //  void nScriptForEach(int id, int slot, int ain, int aout, byte[] params) {
                    initMethod.invoke(mRS, scriptPointer, kernelSlot, in[0], out, null);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method nScriptForEach;

    private Method getMethodnScriptForEach() {
        if (nScriptForEach == null)
            try {
                Method method;

                switch (Build.VERSION.SDK_INT) {
                    case Build.VERSION_CODES.LOLLIPOP:
                    case Build.VERSION_CODES.LOLLIPOP_MR1:
                        // synchronized void nScriptForEach(long id, int slot, long ain, long aout, byte[] params) {
                        method = RenderScript.class.getDeclaredMethod("nScriptForEach", long.class, int.class, long.class, long.class, byte[].class);
                        break;
                    case Build.VERSION_CODES.M:
                        // void nScriptForEach(long id, int slot, long[] ains, long aout, byte[] params, int[] limits)
                        method = RenderScript.class.getDeclaredMethod("nScriptForEach", long.class, int.class, long[].class, long.class, byte[].class, int[].class);
                        break;
                    case Build.VERSION_CODES.KITKAT:
                    default:
                        //  void nScriptForEach(int id, int slot, int ain, int aout, byte[] params) {
                        method = RenderScript.class.getDeclaredMethod("nScriptForEach", int.class, int.class, int.class, int.class, byte[].class);

                }

                method.setAccessible(true);

                nScriptForEach = method;

                return method;
            } catch (NoSuchMethodException e) {
                throw new RuntimeException("nScriptForEach method does not exists");

            }

        return nScriptForEach;
    }

    long getAllocationPointer(Allocation allocation) {
        Field allocationIDField = null;
        long AllocationID = 0;
        try {

            allocationIDField = allocation.getClass().getSuperclass().getDeclaredField("mID");
            allocationIDField.setAccessible(true);
            AllocationID = (long) allocationIDField.getLong(allocation);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access Allocation ID");
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Could not find Allocation ID");
        }

        if (AllocationID == 0) {
            throw new RuntimeException("Invalid allocation ID");
        }
        return AllocationID;
    }

}
