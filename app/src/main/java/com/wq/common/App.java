package com.wq.common;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.widget.ImageView;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.sunrun.sunrunframwork.app.BaseApplication;
import com.sunrun.sunrunframwork.common.DefaultMediaLoader;
import com.sunrun.sunrunframwork.common.IMediaLoader;
import com.sunrun.sunrunframwork.http.NetServer;
import com.tencent.bugly.Bugly;
import com.wq.common.boxing.BoxingGlideLoader;
import com.wq.common.boxing.BoxingUcrop;
import com.wq.common.boxing.FixDefaultMediaLoader;
import com.wq.common.util.OtherDataConvert;
import com.wq.common.util.OtherRequestPreproccess;
import com.wq.common.util.PushHelper;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by WQ on 2017/5/24.
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        Bugly.init(getApplicationContext(), "5808d7e434", false);
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
        NetServer.Settings.getSetting().setDataConvert(new OtherDataConvert());
        NetServer.Settings.getSetting().setRequestPreproccess(new OtherRequestPreproccess());
        ((DefaultMediaLoader)DefaultMediaLoader.getInstance()).init(new FixDefaultMediaLoader());
        PushHelper.initPush(this);
       // CrashReport.initCrashReport(getApplicationContext(), "5808d7e434", false);
//        NetServer.Settings.getSetting().setShowLog(false);
//        JPushInterface.setDebugMode(true);
//        JPushInterface.init(this);
}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}
