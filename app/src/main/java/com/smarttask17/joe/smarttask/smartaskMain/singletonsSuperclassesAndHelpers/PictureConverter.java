package com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

/**
 * Used for creating circular images from:
 * http://stackoverflow.com/questions/43378223/circular-imageview-in-android
 */

public class PictureConverter extends AppCompatImageView{


    public PictureConverter(Context context) {
        super(context);
    }

    public PictureConverter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PictureConverter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        Bitmap b = ((BitmapDrawable) drawable).getBitmap();
        Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);

        int w = getWidth();
        @SuppressWarnings("unused")
        int h = getHeight();

        Bitmap roundBitmap = getNewRoundProfilePicture(bitmap, w);
        canvas.drawBitmap(roundBitmap, 0, 0, null);

    }

    /**
     * @param radius: use around 100 for small profile picture and 300 for bigger pictures
     * */
    public static Bitmap getRoundProfilePicture(String path, int radius){
        return getNewRoundProfilePicture(getBitmap(path), radius);
    }

    private static Bitmap getNewRoundProfilePicture(Bitmap bmp, int radius) {
        Bitmap sbmp;
        if (bmp.getWidth() != radius || bmp.getHeight() != radius) {
            float smallest = Math.min(bmp.getWidth(), bmp.getHeight());
            float factor = smallest / radius;
            sbmp = Bitmap.createScaledBitmap(bmp,
                    (int) (bmp.getWidth() / factor),
                    (int) (bmp.getHeight() / factor), false);
        } else {
            sbmp = bmp;
        }
        Bitmap output = Bitmap.createBitmap(radius, radius, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);
        final String color = "#BAB399";
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, radius, radius);
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor(color));
        canvas.drawCircle(radius / 2 + 0.7f, radius / 2 + 0.7f,
                radius / 2 + 0.1f, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(sbmp, rect, rect, paint);
        return output;
    }

        /**
         * @inSampleSize set to 1 for full picture. For 1/4 of the size set to 4...
        * */
    public static Bitmap getSquareBitmap(String path,int inSampleSize ) {
        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        options.inSampleSize = inSampleSize;
        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        // Make bitmap square
//        Bitmap dstBmp;
//        if (bitmap.getWidth() >= bitmap.getHeight()){
//            dstBmp = Bitmap.createBitmap(
//                    bitmap,
//                    bitmap.getWidth()/2 - bitmap.getHeight()/2,
//                    0,
//                    bitmap.getHeight(),
//                    bitmap.getHeight()
//            );
//
//        }else{
//
//            dstBmp = Bitmap.createBitmap(
//                    bitmap,
//                    0,
//                    bitmap.getHeight()/2 - bitmap.getWidth()/2,
//                    bitmap.getWidth(),
//                    bitmap.getWidth()
//            );
//        }

        // Read in and create final bitmap
        return bitmap;
    }



    public static Bitmap getBitmap(String path){
        return BitmapFactory.decodeFile(path);
    }
}
