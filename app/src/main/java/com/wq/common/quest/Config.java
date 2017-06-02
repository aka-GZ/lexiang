package com.wq.common.quest;

import com.google.gson.reflect.TypeToken;
import com.sunrun.sunrunframwork.http.BaseConfig;
import com.sunrun.sunrunframwork.http.NAction;
import com.wq.common.model.LoginInfo;


/**
 * App配置信息
 */

public class Config extends BaseConfig {

    public static final String START_NUM = "run_num";// 程序启动次数
    public static final String START_IMG = "guide";// 引导页图片路径

    /**
     * 获取登录信息
     *
     * @return 不会为null(没有时返回空的LoginInfo对象)
     */
    public static LoginInfo getLoginInfo() {
        LoginInfo info = getDataCache(BaseConfig.LOGIN_INFO,
                new TypeToken<LoginInfo>() {
                });
        return info == null ? new LoginInfo() : info;
    }

    public static NAction  UAction(){
        return new NAction().put("member_id",getLoginInfo().getId());
    }

}
