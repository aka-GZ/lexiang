package com.wq.common.util;

import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.NetUtils;
import com.sunrun.sunrunframwork.http.RequestPreproccess;
import com.wq.common.quest.Config;

/**
 * 接口请求参数提交前的一些预处理工作
 * Created by WQ on 2017/6/3.
 */

public class OtherRequestPreproccess implements RequestPreproccess {
  static   boolean hasToken=false;
    @Override
    public NAction proccess(NAction nAction) {

        nAction.params.setUseJsonStreamer(true);
        //if(!hasToken && Config.getLoginInfo().getToken()!=null) {
            //添加请求头
            hasToken=true;
            NetUtils.getAsynHttpClient().addHeader("token",Config.getLoginInfo().getToken());
       // }
        return nAction;
    }

    /**
     * 清除token缓存
     */
    public static void clearToken(){
        hasToken=false;
        NetUtils.getAsynHttpClient().addHeader("token",null);
    }
}
