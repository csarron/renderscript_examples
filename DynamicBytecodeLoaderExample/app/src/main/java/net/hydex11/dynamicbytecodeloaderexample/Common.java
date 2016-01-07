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

package net.hydex11.dynamicbytecodeloaderexample;

import android.os.Environment;
import android.os.Handler;
import android.os.Looper;

import java.io.File;

public class Common {
    // Custom RenderScript cache path. RS uses by default one, but for our usage, it is more
    // convenient to define a different one, as we should not interfere with other rs scripts
    public static final String externalDirPath = new File(Environment.getExternalStorageDirectory(), "net.hydex11.dynamicbytecodeloader").getAbsolutePath();

    // Function to run a callback on UI thread
    public static void runOnUiThread(Runnable runnable) {
        final Handler UIHandler = new Handler(Looper.getMainLooper());
        UIHandler.post(runnable);
    }

    // Util class to store script bytecode
    public static class ScriptData {
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

    // Checks that download dir exists
    public static void dirChecker(String directoryPath) {
        File f = new File(directoryPath);

        if(!f.isDirectory()) {
            f.mkdirs();
        }
    }
}

