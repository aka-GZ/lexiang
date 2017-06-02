package com.wq.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sunrun.sunrunframwork.uibase.BaseFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @作者: Liufan
 * @时间: 2017/5/22
 * @功能描述: 预留的, 为增加功能做准备
 */


public abstract class LBaseFragment extends BaseFragment {
    abstract public @LayoutRes int getLayoutRes();
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int layoutRes = getLayoutRes();
        if (layoutRes > 0) {
            return inflater.inflate(layoutRes, container, false);
        }else{
            throw new RuntimeException("getLayoutRes should be override to provide the layout resource");
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder=ButterKnife.bind(this, view);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
    void onVisible() {

    }

    void onInVisible() {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            onInVisible();
        }else{
            onVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            onVisible();
        }else{
            onInVisible();
        }
    }
}
