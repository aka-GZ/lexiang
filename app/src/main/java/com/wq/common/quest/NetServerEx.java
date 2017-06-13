package com.wq.common.quest;

import android.content.Context;

import com.loopj.android.http.RequestHandle;
import com.loopj.android.http.TextHttpResponseHandler;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.NetServer;
import com.sunrun.sunrunframwork.http.NetUtils;
import com.sunrun.sunrunframwork.http.utils.JsonDeal;
import com.sunrun.sunrunframwork.http.utils.UIUpdateHandler;
import com.sunrun.sunrunframwork.utils.log.Logger;

import org.apache.http.Header;
import org.apache.http.entity.ByteArrayEntity;

/**
 * Created by weiquan on 2017/6/10.
 */

public class NetServerEx extends NetServer {
    Context context;
    public NetServerEx(Context context, UIUpdateHandler uiUpdateHandler) {
        super(context, uiUpdateHandler);
        this
                .context=context;
    }

    public NetServerEx(Context context) {
        super(context);
        this.context=context;
    }

    protected void doRequest(final NAction action) {
       if(action.getRequestType()==POST){
           NetServer.Settings.getSetting().getRequestPreproccess().proccess(action);
           final boolean useCache = action.useCache;
           boolean cachePriority = action.cachePriority;

           RequestHandle handler = null;
           TextHttpResponseHandler responseHandler = new TextHttpResponseHandler() {
               public void onSuccess(int arg0, Header[] arg1, String json) {
                  Logger.D((arg0 == -2147483648?"CACHE: ":"") + (action.requestType == 2321?"GET: ":"POST: ") + action.url + "?"  + "\n请求成功: " + json);
                   BaseBean bean = (BaseBean)NetServer.Settings.getSetting().getDataConvert().convert(action, json);
                   if(arg0 != -2147483648) {
                       if(uiUpdateHandler.getSession() != null && !bean.isEmpty()) {
                           uiUpdateHandler.getSession().put("" + action.hashCode(), json);
                       }

                       bean.setCache(useCache);
                   }

                  dispatch(action, bean);
                   if(bean.isEmpty()) {
                  uiUpdateHandler.emptyData(action.requestCode, bean);
                   }

               }

               public void onStart() {
                   uiUpdateHandler.requestStart();
               }

               public void onFinish() {
                 cancelRequest(action.requestCode);
                  uiUpdateHandler.requestFinish();
               }

               public void onCancel() {
                   Logger.D((action.requestType == 2321?"GET: ":"POST: ") + action.url + "?" + action.params + "\n请求取消");
                 cancelRequest(action.requestCode);
                uiUpdateHandler.requestCancel();
                   BaseBean bean = new BaseBean();
                   bean.msg = "请求取消";
               }

               public void onProgress(long bytesWritten, long totalSize) {
                 uiUpdateHandler.nofityUpdate(action.requestCode, (float)bytesWritten * 1.0F / (float)totalSize);
               }

               public void onFailure(int arg0, Header[] arg1, String arg2, Throwable arg3) {
                   if(useCache) {
                       String bean = uiUpdateHandler.getSession().getString("" + action.hashCode());
                       if(bean != null) {
                           this.onSuccess(-2147483648, (Header[])null, bean);
                           return;
                       }
                   }

                   Logger.D((action.requestType == 2321?"GET: ":"POST: ") + action.url + "?"  + "\n请求失败: " + arg2 + "  " + arg3);
                   BaseBean bean1 = new BaseBean();
                   bean1.msg = NetUtils.getExceptionMsg(arg3, arg2);
                   uiUpdateHandler.loadFaild(action.requestCode, bean1);
               }
           };
         //  handler = NetUtils.doPost(action.url, action.params, responseHandler);
           String jsonContent=JsonDeal.object2Json(action.getTreeMap());
           ByteArrayEntity be = new ByteArrayEntity(jsonContent.getBytes());
           NetUtils.getAsynHttpClient().post(context,action.url,be,"application/json",responseHandler);
       }else {
           super.doRequest(action);
       }
    }
}
