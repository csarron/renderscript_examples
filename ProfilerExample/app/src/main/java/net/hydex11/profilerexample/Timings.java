package net.hydex11.profilerexample;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Alberto on 02/01/2016.
 */
public class Timings {

    // Timestamp got on last addTiming call
    private long lastTimingsTimestamp = 0;

    // Counter to count timings cycles. When this counter reaches timingDebugInterval,
    // profiler will output its code to LogCat
    private int timingDebugCounter = 0;
    private int timingDebugInterval = 10;

    // Profilings are stores as HashMap and their calculation is based on average time
    private ArrayList<String> timingKeys = new ArrayList<>();
    private HashMap<String, ArrayList<Long>> timings = new HashMap<>();

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

    // Interface for function to be called before each timing cycle
    public interface TimingCallback {
        void run();
    }

    // Constructor
    public Timings(){
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
        lastTimingsTimestamp = java.lang.System.currentTimeMillis();
    }

    // Logs a new timing
    public void addTiming(String tag) {
        if (!timings.containsKey(tag)) {
            // New timing
            timingKeys.add(tag);
            timings.put(tag, new ArrayList<Long>());
        }

        // If callback function exists, call it and waits for its completion
        if (timingCallback != null) {
            timingCallback.run();
        }

        long now =  java.lang.System.currentTimeMillis();

        timings.get(tag).add(now - lastTimingsTimestamp);

        lastTimingsTimestamp = now;

    }

    // Function to be called on every loop cycle, that checks if current profiling cycle has to end.
    // If it has to end, prints debug data to LogCat.
    public void debugTimings() {
        if (BuildConfig.DEBUG) {
            timingDebugCounter++;

            if (timingDebugCounter % timingDebugInterval == 0) {

                for (String tag : timingKeys) {
                    double count = timings.get(tag).size();
                    double timeSum = 0;

                    // Calculate avg for timing
                    for (int i = 0; i < count; i++) {
                        timeSum += timings.get(tag).get(i);
                    }

                    double avg = timeSum / count;

                    Log.d("Timings", tag + ": " + avg + "ms");
                }

                clearTimings();
            }

        }
    }
}
