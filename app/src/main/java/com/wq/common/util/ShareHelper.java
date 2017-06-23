package com.wq.common.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import com.sunrun.sunrunframwork.pay.PayUtils;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.AppUtils;
import com.sunrun.sunrunframwork.utils.shareutils.SharedUtil;
import com.wq.common.App;
import com.wq.common.model.TemplateDataObj;
import com.wq.template.ShopTemplateDataActivity;

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
        ToastUtils.shortToast("分享");
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
    public void saveShareImage(final TemplateDataObj obj,boolean locaNeedShare) {

        if(!needShare) {
            needShare = locaNeedShare;return;
        }
        if(locaNeedShare){
            if(!AppUtils.checkPackage(activity,"com.tencent.mm")){
                ToastUtils.shortToast("请安装微信后再次尝试");
                return;
            }
            UIUtils.showLoadDialog(activity);
        }else {
            imageList.clear();
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

    private Runnable cancelDialog() {

        return new Runnable() {
            @Override
            public void run() {
                AHandler.runTask(new AHandler.Task() {
                    @Override
                    public void task() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        super.task();
                    }

                    @Override
                    public void update() {
                        UIUtils.cancelLoadDialog();
                    }
                });
            }
        };
    }
}
