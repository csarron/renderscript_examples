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

package net.hydex11.profilerexample;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by Alberto on 02/01/2016.
 */
public class Timings {
    public static final String TAG = "Timings";

    private Context context;

    // Timestamp got on last addTiming call
    private double lastTimingsTimestamp = 0;

    // Counter to count timings cycles. When this counter reaches timingDebugInterval,
    // profiler will output its code to LogCat
    private int timingDebugCounter = 0;
    private int timingDebugInterval = 50;

    // Profilings are stores as HashMap and their calculation is based on average time
    private ArrayList<String> timingKeys = new ArrayList<>();
    private HashMap<String, ArrayList<Double>> timings = new HashMap<>();

    // Callback function (sync) to be used before taking the current time. Useful to test single
    // RenderScript kernels, as we can this way call mRS.finish() before each of them
    private TimingCallback timingCallback;

    // Sets desired cycles count when we want the output to be written to LogCat
    public void setTimingDebugInterval(int timingDebugInterval) {
        this.timingDebugInterval = timingDebugInterval;
    }

    // Sets custom function to be called before each timing cycle
    public void setTimingCallback(TimingCallback timingCallback) {
        this.timingCallback = timingCallback;
    }

    // Enables stats to be saved to disk
    private boolean saveStatsToDisk = false;
    private BufferedWriter statsFileWriter;
    private Uri statsFileUri;

    public void enableSaveStats(boolean enable) throws IOException {
        // If save is enabled, stats are stored in memory, and saved
        // to disk on request. Then, the CSV file gets sent to an
        // intent.
        saveStatsToDisk = enable;

        if (saveStatsToDisk && statsFileWriter == null) {
            // Creates a custom folder inside the external dir
            File rsProfilerDir = new File(Environment.getExternalStorageDirectory() + File.separator + "RSProfiler");
            rsProfilerDir.mkdirs();
            String phoneDataString = String.format("%s_%d", Build.MODEL.replaceAll("\\s", ""), Build.VERSION.SDK_INT);
            File csvFile = new File(rsProfilerDir, "RSProfilerData_" + phoneDataString + ".csv");
            statsFileUri = Uri.fromFile(csvFile);
            // Instantiates a new buffered writer
            statsFileWriter = new BufferedWriter(new FileWriter(csvFile));

            // Writes header
            statsFileWriter.write("Tag,Timing\n");
        } else {
            if (statsFileWriter != null) {
                statsFileWriter.close();
                statsFileWriter = null;
            }
        }
    }

    // The following limit is used when wanting to limit the maximum executions of code.
    // When the limit is reached, sendStats function gets called and application exits.
    private int statSaveCountLimit = 0;

    public void setStatsSaveCountLimit(int limit) {
        statSaveCountLimit = limit;
    }

    // Function that gets called when there is the need to send the CSV file to
    // an intent
    public void sendStats() throws IOException {
        if (saveStatsToDisk && statsFileWriter != null) {
            statsFileWriter.close();
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_SUBJECT, "RenderScript Profiler data");
            sendIntent.putExtra(Intent.EXTRA_STREAM, statsFileUri);
            sendIntent.setType("text/html");
            context.startActivity(sendIntent);
        }
    }

    // Interface for function to be called before each timing cycle
    public interface TimingCallback {
        void run();
    }

    // Constructor
    public Timings(Context context) {
        this.context = context;
        clearTimings();
    }

    // Function that resets all timings. Used after profiler completes a timing cycle (after
    // having reached timingDebugInterval count
    public void clearTimings() {
        timingKeys.clear();
        timings.clear();
        timingDebugCounter = 0;
    }

    // Function to be called at the beginning of every calculation loop cycle
    public void initTimings() {
        lastTimingsTimestamp = java.lang.System.nanoTime();
    }

    // Logs a new timing
    private long totalSamples = 0;

    public void addTiming(String tag, Object... args) {
        addTiming(String.format(tag, args));
    }
    public void addTiming(String tag) {

        // If callback function exists, call it and waits for its completion
        if (timingCallback != null) {
            timingCallback.run();
        }

        double now = java.lang.System.nanoTime();
        double elapsed = (now - lastTimingsTimestamp) / 1000000; // Nanoseconds to milliseconds

        if (saveStatsToDisk && statsFileWriter != null)
            try {
                // If write to file is enabled, replaces commas in the tag with underscores
                // and writes timing to CSV file
                statsFileWriter.write(tag.replaceAll(",", "_") + "," + elapsed + "\n");
            } catch (IOException e) {
                Log.e(TAG, "Stats file writer exception", e);
            }

        if (!timings.containsKey(tag)) {
            // New timing
            timingKeys.add(tag);
            timings.put(tag, new ArrayList<Double>());
        }
        timings.get(tag).add(elapsed);
        totalSamples++;

        if (statSaveCountLimit > 0 && totalSamples >= statSaveCountLimit) {
            try {
                sendStats();
                System.exit(0);
            } catch (IOException e) {
                throw new RuntimeException("Could not send saved data", e);
            }
        }

        resetLastTimingsTimestamp();
    }

    public void resetLastTimingsTimestamp() {
        lastTimingsTimestamp = java.lang.System.nanoTime();
    }

    // Function to be called on every loop cycle, that checks if current profiling cycle has to end.
    // If it has to end, prints debug data to LogCat.
    public void debugTimings() {
        timingDebugCounter++;

        if (timingDebugCounter % timingDebugInterval == 0) {

            double count, timeSum, currentTiming, avg;

            for (String tag : timingKeys) {
                count = timings.get(tag).size();
                timeSum = 0;

                // Calculate avg for timing
                for (int i = 0; i < count; i++) {
                    currentTiming = timings.get(tag).get(i);
                    if (currentTiming == 0) continue;
                    timeSum += 1.0 / currentTiming;
                }

                avg = count / timeSum;

                Log.i(TAG, tag + ": " + String.format("%.3f", avg) + "ms");
            }

            Log.i(TAG, "Total samples: " + totalSamples);

            clearTimings();
        }

    }
}
