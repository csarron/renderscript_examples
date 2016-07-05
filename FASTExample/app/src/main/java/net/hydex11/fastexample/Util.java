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

package net.hydex11.fastexample;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by cmaster11 on 6/28/16.
 */
public class Util {
    public static void saveImageToExternal(Context context, String imgName, Bitmap bm) throws IOException {

        ContentResolver cr = context.getContentResolver();

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, imgName + ".png");
        values.put(MediaStore.Images.Media.DESCRIPTION, imgName);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png");

        Uri url = cr.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        OutputStream imageOut = cr.openOutputStream(url);
        try {
            bm.compress(Bitmap.CompressFormat.PNG, 100, imageOut); // Compress Image
        } catch (Exception ex) {
            throw ex;
        } finally {
            imageOut.close();
        }

        long id = ContentUris.parseId(url);

        // Wait until MINI_KIND thumbnail is generated.
        Bitmap miniThumb = MediaStore.Images.Thumbnails.getThumbnail(cr, id,
                MediaStore.Images.Thumbnails.MINI_KIND, null);

    }
}
