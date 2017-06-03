package com.wq.common.util;

import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.RequestPreproccess;

/**
 * 接口请求参数提交前的一些预处理工作
 * Created by WQ on 2017/6/3.
 */

public class OtherRequestPreproccess implements RequestPreproccess {
    @Override
    public NAction proccess(NAction nAction) {

        nAction.params.setUseJsonStreamer(true);
        return nAction;
    }
}
