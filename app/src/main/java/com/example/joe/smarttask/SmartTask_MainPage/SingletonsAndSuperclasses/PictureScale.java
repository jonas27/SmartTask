package com.example.joe.smarttask.SmartTask_MainPage.SingletonsAndSuperclasses;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by joe on 05/05/2017.
 */

public class PictureScale {

    private PictureScale(){}

    public static Bitmap getScaledBitmap(String path, int destWidth, int destHeight,int inSampleSize) {
        // Read in the dimensions of the image on disk
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        float srcWidth = options.outWidth;
        float srcHeight = options.outHeight;
        // Figure out how much to scale down by
        if (srcHeight > destHeight || srcWidth > destWidth) {
            if (srcWidth > srcHeight) {
                inSampleSize = Math.round(srcHeight / destHeight);
            } else {
                inSampleSize = Math.round(srcWidth / destWidth);
            }
        }
        options = new BitmapFactory.Options();
        options.inSampleSize = inSampleSize;
        // Read in and create final bitmap
        return BitmapFactory.decodeFile(path, options);
    }
}
