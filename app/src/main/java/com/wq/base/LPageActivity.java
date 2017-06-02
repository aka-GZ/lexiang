package com.wq.base;

/**
 * @作者: Liufan
 * @时间: 2017/5/19
 * @功能描述:
 */

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;

import com.sunrun.sunrunframwork.uibase.PagingActivity;
import com.wq.project01.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 预留后面会增加的空间
 */

public abstract class LPageActivity<T> extends PagingActivity<T> {

    Unbinder unbinder;
    protected boolean ifTitleBarIsWhiteThenIWillSetBlackStatusBarOnNotCompatDevice() {
        return isStatusContentDark();
    }

    protected boolean isStatusContentDark() {
        return false;
    }

    protected boolean isTranslucent() {
        return true;
    }
    private int getStatusColorInStyle() {
        int[] attrs = new int[]{R.attr.colorPrimaryDark};
        TypedArray typedArray = obtainStyledAttributes(attrs);
        int backgroundResource = typedArray.getColor(0, Color.BLACK);
        typedArray.recycle();
        return backgroundResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isTranslucent()) {
            StatusBarUtil.transparencyBar(this);
        }
        if (isStatusContentDark()) {
            final int type = StatusBarUtil.StatusBarLightMode(this);
            if (type <= 0 && ifTitleBarIsWhiteThenIWillSetBlackStatusBarOnNotCompatDevice()) {
                StatusBarUtil.setStatusBarColor(this, android.R.color.black);
            }
        }
    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        bindView();
    }

    private void bindView() {
        unbinder= ButterKnife.bind(this);
    }
    private void unbindView() {
        unbinder.unbind();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        bindView();
    }
    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        bindView();
    }
    @Override
    protected void onDestroy() {
        unbindView();
        super.onDestroy();
    }
}

