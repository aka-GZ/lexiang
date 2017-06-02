package com.wq.base;

/**
 * @作者: Liufan
 * @时间: 2017/5/19
 * @功能描述:
 */

/**
 * 预留后面会增加的空间
 */

public class LBaseActivity extends TranslucentActivity {
    @Override
    protected boolean isStatusContentDark() {
        return false;
    }

    @Override
    protected boolean isTranslucent() {
        return true;
    }
}
