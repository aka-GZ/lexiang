package com.wq.common.util;

import com.loopj.android.http.RequestParams;
import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.NetRequestHandler;
import com.sunrun.sunrunframwork.http.NetUtils;
import com.sunrun.sunrunframwork.http.RequestPreproccess;
import com.sunrun.sunrunframwork.http.utils.JsonDeal;
import com.sunrun.sunrunframwork.utils.Utils;
import com.sunrun.sunrunframwork.utils.log.Logger;
import com.wq.common.quest.Config;

import org.apache.http.entity.StringEntity;

import java.io.ByteArrayInputStream;
import java.util.Map;

import static com.wq.common.quest.BaseQuestConfig.PAGE_COUNT;

/**
 * 接口请求参数提交前的一些预处理工作
 * Created by WQ on 2017/6/3.
 */

public class OtherRequestPreproccess implements RequestPreproccess {
    static boolean hasToken = false;

    @Override
    public NAction proccess(NAction nAction) {
        //nAction.put("","");

        //if(!hasToken && Config.getLoginInfo().getToken()!=null) {
        //添加请求头
        hasToken = true;
        Map<String ,Object > treeMap=nAction.getTreeMap();
        if(treeMap.get("firstRow")!=null){
            int firstRow= (int) Utils.valueOf(String.valueOf(treeMap.get("firstRow")),0);
            int pageCount= (int) Utils.valueOf(String.valueOf(treeMap.get("listRows")),PAGE_COUNT);
            nAction.put("firstRow",(firstRow)*pageCount);
        }
        NetUtils.getAsynHttpClient().addHeader("token", Config.getLoginInfo().getToken());
        Logger.D(nAction.toString());
//        NetUtils.getAsynHttpClient().addHeader("token","88568ef65dcb446aa084352742f3fe19");


        // }
        return nAction;
    }

    /**
     * 清除token缓存
     */
    public static void clearToken() {
        hasToken = false;
        NetUtils.getAsynHttpClient().addHeader("token", null);
    }
}
