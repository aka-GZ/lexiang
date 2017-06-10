package com.wq.base;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.NAction;
import com.sunrun.sunrunframwork.http.NetRequestHandler;
import com.sunrun.sunrunframwork.http.NetServer;
import com.sunrun.sunrunframwork.http.cache.NetSession;
import com.sunrun.sunrunframwork.http.utils.UIUpdateHandler;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.StatisticsUtil;
import com.sunrun.sunrunframwork.utils.log.Logger;
import com.wq.common.quest.NetServerEx;
import com.wq.project01.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.view.WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
import static android.view.WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE;

/**
 * 对话框Fragment基类
 * Created by WQ on 2017/4/26.
 */

public abstract class UIBindDialogFragment extends DialogFragment implements
        NetRequestHandler, UIUpdateHandler {
    protected FragmentActivity mAct;
    protected NetServer mNServer;
    protected Activity that;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mNServer=new NetServerEx(that,this);
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder=ButterKnife.bind(this, view);
        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = new Dialog(getActivity(), R.style.NoTitleDialog);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setSoftInputMode(SOFT_INPUT_STATE_ALWAYS_VISIBLE | SOFT_INPUT_ADJUST_RESIZE);
        //设置对话框从底部进入
        dialogWindow.setWindowAnimations(R.style.bottomInWindowAnim);
        WindowManager.LayoutParams p = dialogWindow.getAttributes();
        p.width = ViewGroup.LayoutParams.MATCH_PARENT;
        p.height = ViewGroup.LayoutParams.MATCH_PARENT;//高度自己设定
        dialogWindow.setAttributes(p);
        dialogWindow.setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialogWindow.getDecorView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        return dialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    protected abstract int getLayoutId();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        that = mAct = getActivity();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        StatisticsUtil.pageStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        StatisticsUtil.pageEnd(this);
    }

    /**
     * 初始化网络请求模块,由子类在有需要时调用
     */
    protected void initNetServer() {
        if (mNServer == null)
            mNServer = new NetServer(mAct, this);
    }

    @Override
    public void requestAsynGet(NAction action) {
        initNetServer();
        mNServer.requestAsynGet(action);

    }

    @Override
    public void useCache(boolean useCache) {
        initNetServer();
        mNServer.useCache(useCache);
    }

    @Override
    public void requestAsynPost(NAction action) {
        initNetServer();
        mNServer.requestAsynPost(action);
    }

    @Override
    public void cancelRequest(int requestCode) {
        if (mNServer != null)
            mNServer.cancelRequest(requestCode);
    }

    @Override
    public void cancelAllRequest() {
        if (mNServer != null)
            mNServer.cancelAllRequest();
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {

    }

    @Override
    public void nofityUpdate(int requestCode, float progress) {

    }

    @Override
    public void dealData(int requestCode, BaseBean bean) {

    }

    @Override
    public void loadFaild(int requestCode, BaseBean bean) {

    }

    @Override
    public void emptyData(int requestCode, BaseBean bean) {

    }

    @Override
    public void requestStart() {

    }

    @Override
    public void requestCancel() {
        UIUtils.cancelLoadDialog();
    }

    @Override
    public void requestFinish() {
        UIUtils.cancelLoadDialog();
    }


    @Override
    public NetSession getSession() {
        return NetSession.instance(mAct);
    }

    @Override
    public void onDestroyView() {
        cancelAllRequest();
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.D("视图销毁 " + this);
        that = null;
    }


    public void finish() {
        if (mAct != null)
            mAct.finish();
    }
}
