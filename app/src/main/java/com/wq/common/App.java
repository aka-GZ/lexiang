package com.wq.common;

import com.bilibili.boxing.BoxingCrop;
import com.bilibili.boxing.BoxingMediaLoader;
import com.bilibili.boxing.loader.IBoxingMediaLoader;
import com.sunrun.sunrunframwork.app.BaseApplication;
import com.sunrun.sunrunframwork.http.NetServer;
import com.wq.common.boxing.BoxingGlideLoader;
import com.wq.common.boxing.BoxingUcrop;
import com.wq.common.util.OtherDataConvert;

/**
 * Created by WQ on 2017/5/24.
 */

public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        IBoxingMediaLoader loader = new BoxingGlideLoader();
        BoxingMediaLoader.getInstance().init(loader);
        BoxingCrop.getInstance().init(new BoxingUcrop());
        NetServer.Settings.getSetting().setDataConvert(new OtherDataConvert());
    }

}
