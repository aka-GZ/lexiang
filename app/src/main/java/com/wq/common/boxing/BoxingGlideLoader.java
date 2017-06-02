package com.wq.common.boxing;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.bilibili.boxing.loader.IBoxingCallback;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.wq.project01.R;


/**
 * Created by cnsunrun on 2017/3/20.
 * <p>
 * b站图片加载库Glide实现类
 */

public class BoxingGlideLoader implements IBoxingMediaLoader {

    @Override
    public void displayThumbnail(@NonNull ImageView img, @NonNull String absPath, int width, int height) {
        String path = "file://" + absPath;
        try {
            // https://github.com/bumptech/glide/issues/1531
            Glide.with(img.getContext()).load(path).placeholder(R.drawable.ic_default_image).crossFade().centerCrop().into(img);
        } catch (IllegalArgumentException ignore) {
        }

    }

    @Override
    public void displayRaw(@NonNull final ImageView img, @NonNull String absPath, final IBoxingCallback callback) {
        String path = "file://" + absPath;
        Glide.with(img.getContext())
                .load(path)
                .asBitmap()
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        if (callback != null) {
                            callback.onFail(e);
                            return true;
                        }
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (resource != null && callback != null) {
                            img.setImageBitmap(resource);
                            callback.onSuccess();
                            return true;
                        }
                        return false;
                    }
                })
                .into(img);

    }

}
