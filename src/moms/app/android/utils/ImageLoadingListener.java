package moms.app.android.utils;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ProgressBar;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.SimpleImageLoadingListener;

//private inner class to represent loading spinner on images in polls
public class ImageLoadingListener extends SimpleImageLoadingListener {

    final ProgressBar spinner;

    public ImageLoadingListener(ProgressBar spinner){
        this.spinner = spinner;
    }

    @Override
    public void onLoadingStarted(String imageUri, View view) {
        spinner.setVisibility(View.VISIBLE);
    }

    @Override
    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
        spinner.setVisibility(View.GONE);
    }

    @Override
    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
        spinner.setVisibility(View.GONE);
    }
}
