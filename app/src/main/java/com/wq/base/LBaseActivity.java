package com.wq.base;

/**
 * @作者: Liufan
 * @时间: 2017/5/19
 * @功能描述:
 */

import android.os.Bundle;

import com.wq.common.quest.NetServerEx;

/**
 * 预留后面会增加的空间
 */

public class LBaseActivity extends TranslucentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mNServer=new NetServerEx(this,this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected boolean isStatusContentDark() {
        return false;
    }

    @Override
    protected boolean isTranslucent() {
        return true;
    }
}
