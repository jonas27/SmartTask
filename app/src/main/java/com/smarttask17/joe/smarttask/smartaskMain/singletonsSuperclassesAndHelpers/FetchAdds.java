package com.smarttask17.joe.smarttask.smartaskMain.singletonsSuperclassesAndHelpers;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Placeholder for adds not done yet
 * For future!
 */

public class FetchAdds {

    public static final String URL_ADDRESS = "https://carwash.drjones.xyz/img/logo.png";


    public void saveImage(String pictureURL){
        ImageLoader.getInstance().loadImage(pictureURL, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }

}
