package com.wq.common.util;

import com.google.gson.reflect.TypeToken;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.BaseBeanConvert;
import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.utils.JsonDeal;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.common.App;

import org.json.JSONObject;

import static com.wq.common.model.Const.CODE_BUSY;
import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.model.Const.CODE_OUT_LOGIN;

/**
 * 自定义数据解析
 * Created by WQ on 2017/5/26.
 */

public class OtherDataConvert extends BaseBeanConvert {
    @Override
    public BaseBean convert(NAction action, String json) {
        BaseBean bean;
        if(action.resultDataType != null) {
            bean = createBean(json, action.resultDataType);
        } else if(action.typeToken != null) {
            bean = createBean(json, action.typeToken);
        } else {
            bean = createBean(json);
        }
        if(bean.status==CODE_OUT_LOGIN){
            UIUtils.shortM("登录已失效！");
            LoginUtil.exitLogin(App.getInstance());
        }else if (bean.status==CODE_BUSY){
            UIUtils.shortM("请求太频繁，请稍后重试！");
        }
        bean.tag = action.getTag();
        return bean;
    }
    public static BaseBean createBean(String json, Class<?> clazz) {
        JSONObject jobj = JsonDeal.createJsonObj(json);
        BaseBean bean = new BaseBean();
        JSONObject metaObj=jobj.optJSONObject("meta");
        bean.status = metaObj.optInt("code");
        bean.msg = metaObj.optString("message");
        Object obj = jobj.opt("body");
        bean.post_time = jobj.optString("post_time");
        if(bean.status == CODE_OK) {
            bean.data = JsonDeal.json2Object(String.valueOf(obj), clazz);
        }

        return bean;
    }

    public static <T> BaseBean createBean(String json, TypeToken<T> typeToken) {
        JSONObject jobj = JsonDeal.createJsonObj(json);
        BaseBean bean = new BaseBean();
        JSONObject metaObj=jobj.optJSONObject("meta");
        bean.status = metaObj.optInt("code");
        bean.msg = metaObj.optString("message");
        Object obj = jobj.opt("body");
        bean.post_time = jobj.optString("post_time");
        if(bean.status == CODE_OK) {
            bean.data = JsonDeal.json2Object(String.valueOf(obj), typeToken);
        }

        return bean;
    }

    public static <T> BaseBean createBean(String json) {
        JSONObject jobj = JsonDeal.createJsonObj(json);
        BaseBean bean = new BaseBean();
        JSONObject metaObj=jobj.optJSONObject("meta");
        bean.status = metaObj.optInt("code");
        bean.msg = metaObj.optString("message");
        Object obj = jobj.has("body")?jobj.opt("body"):json;
        bean.post_time = jobj.optString("post_time");
        bean.data = String.valueOf(obj);
        return bean;
    }
}
