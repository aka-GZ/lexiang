package com.wq.common.boxing;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.wq.project01.R;


/**
 * Created by WQ on 2017/5/5.
 */

public class GlideMediaLoader {
    public static void load(Object context,View imgview,String path,int placeholder){
        with(context)
                .load(path).centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(placeholder).into((ImageView) imgview);
    }
    public static void load(Object context,View imgview,String path){
        load(context, imgview, path, R.drawable.new_nopic);
    }
    public static void loadHead(Object context,View imgview,String path){
        load(context, imgview, path, R.mipmap.ic_launcher);
    }
   static RequestManager with(Object context){
       if(context instanceof Activity){
           return  Glide.with((Activity) context);
       }else if( context instanceof  Fragment ){
           return Glide.with((Fragment) context);
       }else  if (context instanceof Context){
           return Glide.with((Context) context);
       }
       return null;
   }


}
