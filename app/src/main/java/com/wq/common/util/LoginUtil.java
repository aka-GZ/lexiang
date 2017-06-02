package com.wq.common.util;

import android.app.Activity;
import android.content.Context;

import com.sunrun.sunrunframwork.http.cache.ACache;


/**
 * 登录工具类
 * Created by WQ on 2017/1/10.
 */

public class LoginUtil {
    public static void exitLogin(Context context) {
        exitLogin(context, false);
    }

    public static void exitLogin(Context context, boolean isOrtherLogin) {
//        Config.putLoginInfo(null);
//        Config.putConfigInfo(context, LoginConfig.PWD, null);
//        BaoBaoShuApp.getInstance().closeActivitys(MainActivity.class);
//        StartIntent.startLoginActivity((BaseActivity) context, isOrtherLogin);
        ACache.get(context).clear();
        if (context != null) {
            ((Activity) context).finish();
        }
    }
}
