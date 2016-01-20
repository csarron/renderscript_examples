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

import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ScriptDownloader {
    private static final String TAG = "ScriptDownloader";

    // Host wherefrom to download scripts. Have it finish with a slash!
    private static String remoteUrl = "http://hydex11.net/rsbook/dynscripts/";

    // Download directory
    public static String localDownloadDir = new File(Common.externalDirPath, "downloads").getAbsolutePath();

    // Downloads file synchronously
    // Source: http://www.codejava.net/java-se/networking/use-httpurlconnection-to-download-file-from-an-http-url
    private static final int BUFFER_SIZE = 4096;
    public static String downloadScript(String scriptName){
        Common.dirChecker(localDownloadDir);
        try {
            String fileURL = remoteUrl + scriptName + ".zip";

            Log.d(TAG, "Downloading file " + fileURL);

            URL url = new URL(fileURL);

            HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
            int responseCode = httpConn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                String fileName = "";
                String disposition = httpConn.getHeaderField("Content-Disposition");
                String contentType = httpConn.getContentType();
                int contentLength = httpConn.getContentLength();

                if (disposition != null) {
                    // extracts file name from header field
                    int index = disposition.indexOf("filename=");
                    if (index > 0) {
                        fileName = disposition.substring(index + 10,
                                disposition.length() - 1);
                    }
                } else {
                    // extracts file name from URL
                    fileName = fileURL.substring(fileURL.lastIndexOf("/") + 1,
                            fileURL.length());
                }

                Log.d(TAG, "Content-Type = " + contentType);
                Log.d(TAG, "Content-Disposition = " + disposition);
                Log.d(TAG, "Content-Length = " + contentLength);
                Log.d(TAG, "fileName = " + fileName);

                // opens input stream from the HTTP connection
                InputStream inputStream = httpConn.getInputStream();
                String saveFilePath = localDownloadDir + File.separator + fileName;

                // opens an output stream to save into file
                FileOutputStream outputStream = new FileOutputStream(saveFilePath);

                int bytesRead = -1;
                byte[] buffer = new byte[BUFFER_SIZE];
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }

                outputStream.close();
                inputStream.close();

                Log.d(TAG, "File downloaded");

                return saveFilePath;
            } else {
                Log.d(TAG, "No file to download. Server replied HTTP code: " + responseCode);
            }
            httpConn.disconnect();

        } catch (java.io.IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
