package com.wq.common.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.sunrun.sunrunframwork.pay.PayUtils;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.sunrun.sunrunframwork.uiutils.UIAlertDialogUtil;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.AppUtils;
import com.sunrun.sunrunframwork.utils.shareutils.SharedUtil;
import com.wq.common.App;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.quest.BaseQuestStart;
import com.wq.template.ShopTemplateDataActivity;
import com.wq.template.TemplateDataActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by weiquan on 2017/6/19.
 */

public class ShareHelper {

    ArrayList<Uri> imageList = new ArrayList();
    Activity activity;
    public ShareHelper(Activity activity){
        this.activity=activity;
    }
    public   boolean needShare;
    public void shareToWx(TemplateDataObj obj) {
//        ToastUtils.shortToast("分享");
        Intent weChatIntent = new Intent();
        weChatIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareToTimeLineUI"));

        Log.e("imageList.size()",imageList.size()+"");
        if (imageList.size() == 0){
            activity. runOnUiThread(cancelDialog());
            return;
        }
        weChatIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        weChatIntent.setType("image/*");
        weChatIntent.putExtra(Intent.EXTRA_STREAM, imageList);
        weChatIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        weChatIntent.putExtra("Kdescription", "" + obj.getTemplate_content()); //分享描述
        activity.startActivity(weChatIntent);

        activity.runOnUiThread(cancelDialog());
    }

    /**
     * 保存分享图片
     * @param obj
     * @param locaNeedShare 是否需要即可分享,传false 只做预下载
     */
    Dialog dialog;
    public void saveShareImage(final TemplateDataObj obj,boolean locaNeedShare) {
        AHandler.cancel(task);
//        if(!needShare) {
//            needShare = locaNeedShare;
//        }
        if(locaNeedShare){
            if(!AppUtils.checkPackage(activity,"com.tencent.mm")){
                ToastUtils.shortToast("请安装微信后再次尝试");
                return;
            }
//            UIUtils.showLoadDialog(activity);
            dialog=  AlertDialogUtil.showLoadDialog(activity,"正在打开微信客户端");
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }else {
            imageList.clear();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                int nameIndex=1;
                if(!needShare) {
                    for (TemplateDataObj.ImgListBean o : obj.getImgList()) {

                        try {
                            if(activity==null || activity.isFinishing())return;
                            File file = DownLoadImageSetvice.downImage(activity, "" + nameIndex++, o.getImg_url());
                            Log.e("===========", "保存保存保存保存保存保存保存" + file.getAbsolutePath() + " " + o.getImg_url());

//                                    imageList.add(  FileProvider.getUriForFile(that,getPackageName()+".FileProvider",file));
                            imageList.add(Uri.fromFile(file));
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                            needShare=false;
                            activity.runOnUiThread(cancelDialog());
                            return;
                        } catch (InterruptedException e) {
                            needShare=false;
                            e.printStackTrace();
                            activity.runOnUiThread(cancelDialog());
                            return;
                        }
                    }
                    needShare=true;
                }

                if(needShare)
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            shareToWx(obj);
                        }
                    });
                needShare=true;
            }
        }).start();
    }
    public void cancelDialogShot(){
        if(dialog!=null && dialog.isShowing()){
            dialog.cancel();
        }
    }
    AHandler.Task task;
    private Runnable cancelDialog() {
        AHandler.cancel(task);
        return new Runnable() {
            @Override
            public void run() {
                AHandler.runTask(task=new AHandler.Task() {
                    @Override
                    public void task() {
                        super.task();
                    }

                    @Override
                    public void update() {
                        if(dialog!=null && dialog.isShowing()){
                            dialog.cancel();
                        }
                    }
                },20000);
            }
        };
    }
}
