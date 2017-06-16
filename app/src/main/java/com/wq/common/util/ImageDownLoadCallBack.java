package com.wq.common.util;

import android.graphics.Bitmap;

import java.io.File;

/**
 * Created by Zheng on 2017/6/16.
 */

public interface ImageDownLoadCallBack {


    public void onDownLoadSuccess(File file);
    public void onDownLoadSuccess(Bitmap bitmap);
    public void onDownLoadFailed();
}
