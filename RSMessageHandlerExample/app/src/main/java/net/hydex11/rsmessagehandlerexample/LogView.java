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

package net.hydex11.rsmessagehandlerexample;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * LogView is a scrolling view where chosen LogCat data is displayed.
 */
public class LogView extends ScrollView {
    private static final String TAG = "LogView";

    TextView logTextView;
    String[] logFilters;
    ArrayList<String> alreadyParsed = new ArrayList<>();

    Thread loggingThread;

    static boolean firstRun = true;

    public LogView(Context context) {
        // No filtering
        this(context, (String[]) null);
    }

    public LogView(Context context, String filter) {
        this(context, new String[]{filter});
    }

    public LogView(Context context, String[] filters) {
        super(context);

        Log.d(TAG, "Instantiated new LogView");

        // If is first run in entire program execution, cleans LogCat output, so that
        // we do not get previous executions lines.
        try {
            if (firstRun) {
                Process logCatCleanProcess = null;
                logCatCleanProcess = Runtime.getRuntime().exec("logcat -c");
                logCatCleanProcess.waitFor();
                firstRun = false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Only log rows that contains this filter are shown.
        // For this example, simple filtering is applied (only single-line rows are counted)
        logFilters = filters;

        // Adds a console like text view
        logTextView = new TextView(context);

        setBackgroundColor(Color.BLACK);
        setPadding(5, 5, 5, 5);

        logTextView.setTextColor(Color.GREEN);
        logTextView.setTextSize(12);
        logTextView.setTypeface(Typeface.MONOSPACE);

        // Fill container (scrolling one)
        logTextView.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        this.addView(logTextView);

        initLoggingThread();
    }

    // Logging thread runs separately from UI as it will read LogCat output.
    private void initLoggingThread() {
        loggingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                boolean canRun = true;
                while (canRun) {
                    try {
                        checkForLogs();
                        Thread.sleep(2500, 0);
                    } catch (InterruptedException e) {
                        Log.d(TAG, "Closing LogCat check thread");
                        canRun = false;
                    }
                }
            }
        });
        loggingThread.start();
    }

    private void checkForLogs() {

        try {

            String appId = getContext().getResources().getString(R.string.app_name);

            // Info from: http://stackoverflow.com/questions/19897628/need-to-handle-uncaught-exception-and-send-log-file

            // For Android 4.0 and earlier, you will get all app's log output, so filter it to
            // mostly limit it to your app's output. In later versions, the filtering isn't needed.
            // -d flag closes process after flush.

            String cmd = (Build.VERSION.SDK_INT <= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) ?
                    "logcat -d -v time " + appId + ":v dalvikvm:v System.err:v *:s" :
                    "logcat -d -v time";

            // Reads output, line per line
            Process logCatProcess = Runtime.getRuntime().exec(cmd);
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(logCatProcess.getInputStream()));

            String line = "";
            while ((line = bufferedReader.readLine()) != null) {
                evaluateLogLine(line);
            }

            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    final String logBeginRegex = "^(.*)([DIEWVA]/\\w+\\s*(?:\\(\\s*\\d+\\s*\\))?:\\s*.*)$";
    Pattern logBeginPattern = Pattern.compile(logBeginRegex);

    private void evaluateLogLine(String logLine) {
        logLine = logLine.trim();

        // Empty line
        if (logLine.equals("")) return;

        // Line already parsed
        if (alreadyParsed.contains(logLine)) {
            return;
        }
        alreadyParsed.add(logLine);

        // 01-17 11:55:49.754 W/art     (11096): Suspending all threads took: 5.780ms
        // 01-17 11:59:12.144 I/Timeline(11096): Timeline: Activity_idle id: android.os.BinderProxy@273fb0d2 time:364339488
        Matcher logBeginMatcher = logBeginPattern.matcher(logLine);

        boolean validMatch = logBeginMatcher.matches();
        // Invalid log line
        if (!validMatch) {
            return;
        }

        // Get actual message
        logLine = logBeginMatcher.group(logBeginMatcher.groupCount());

        if (logFilters == null) {
            threadedAddLogLine(logLine);
            return;
        }

        for (String filter : logFilters) {
            if (logLine.contains(filter)) {
                threadedAddLogLine(logLine);
                break;
            }
        }
    }

    // We need to run the add part from the UI thread
    private void threadedAddLogLine(final String logLine) {
        logTextView.post(new Runnable() {
            @Override
            public void run() {
                logTextView.append(logLine + "\n");
            }
        });
    }

    // Public because we can use it even from other places
    public void addFilteredLogLine(String line) {
        evaluateLogLine(line);
    }

    // Public because we can use it even from other places
    public void addLogLine(String line) {
        threadedAddLogLine(line);
    }

    public void destroy() {
        if (loggingThread != null) {
            loggingThread.interrupt();
            loggingThread = null;
        }
    }
}
