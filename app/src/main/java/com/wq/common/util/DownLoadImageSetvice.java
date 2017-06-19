package com.wq.common.util;

/**
 * Created by Zheng on 2017/6/16.
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.sunrun.sunrunframwork.uiutils.UIUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

/**
 * 图片下载
 */
public class DownLoadImageSetvice implements Runnable {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;
    private String name;

    public DownLoadImageSetvice(Context context, String url, String name, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.name = name;
        this.context = context;
    }

    @Override
    public void run() {
        try {
            currentFile=   downImage(context,name, url);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(currentFile.exists() && currentFile.length()!=0){
                callBack.onDownLoadSuccess(currentFile);
            }
        }
    }

    public static File downImage(Context context,String name, String url) throws ExecutionException, InterruptedException {
        Bitmap bitmap ;
        bitmap = Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                .get();
        File  currentFile=null;
        if (bitmap != null) {
            // 在这里执行图片保存方法
           currentFile = saveImageToGallery(bitmap,name);
        }
        return currentFile;
    }

    public static File saveImageToGallery(Bitmap bmp,String filename) {
        // 首先保存图片
        String file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsoluteFile()+"/pics";//注意小米手机必须这样获得public绝对路径
        File appDir = new File(file);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        String fileName1 = filename + ".jpg";
        File currentFile = new File(appDir, fileName1);
        UIUtils.saveBitmapToFile(bmp,currentFile.getAbsolutePath());
//
//        FileOutputStream fos = null;
//        try {
//            fos = new FileOutputStream(currentFile);
//            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//            fos.flush();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (fos != null) {
//                    fos.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return currentFile;
    }
}