package com.wq.common.boxing;

import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.sunrun.sunrunframwork.common.IMediaLoader;

/**
 * Created by WQ on 2017/6/13.
 */

public class FixDefaultMediaLoader implements IMediaLoader {
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        Glide.with(img.getContext()).load(absPath).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(com.sunrun.sunrunframwork.R.drawable.ic_default_image).into(img);
    }

    public void displayImage(@NonNull ImageView img, @NonNull final String absPath, final IMediaLoadeProgressListener mediaLoadeProgressListener) {
        Glide.with(img.getContext()).load(absPath).diskCacheStrategy(DiskCacheStrategy.SOURCE).placeholder(com.sunrun.sunrunframwork.R.drawable.ic_default_image).listener(new RequestListener<String,GlideDrawable>() {
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                if(mediaLoadeProgressListener != null) {
                    mediaLoadeProgressListener.onLoadingFailed(e.getLocalizedMessage());
                }

                return false;
            }

            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                if(mediaLoadeProgressListener != null) {
                    mediaLoadeProgressListener.onLoadingComplete(absPath, resource);
                }

                return false;
            }
        }).into(img);
    }

    public void displayRaw(@NonNull ImageView img, @NonNull String absPath) {
        Glide.with(img.getContext()).load(absPath).placeholder(com.sunrun.sunrunframwork.R.drawable.ic_default_image).crossFade().into(img);
    }

}
