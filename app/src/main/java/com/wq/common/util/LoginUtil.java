package com.wq.common.util;

import android.content.Context;

import com.sunrun.sunrunframwork.http.cache.ACache;
import com.wq.common.App;
import com.wq.common.quest.Config;


/**
 * 登录工具类
 * Created by WQ on 2017/1/10.
 */

public class LoginUtil {
    public static void exitLogin(Context context) {
        exitLogin(context, false);
    }

    public static void exitLogin(Context context, boolean isOrtherLogin) {
        Config.putLoginInfo(null);
//        Config.putConfigInfo(context, LoginConfig.PWD, null);
        App.getInstance().closeAllActivity();
        PushHelper.stopPush(context);
        IntentUtil.startLogin( context, isOrtherLogin);
        ACache.get(context).clear();
    }
}
