package com.wq.jpush;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;

import com.sunrun.sunrunframwork.http.utils.JsonDeal;
import com.sunrun.sunrunframwork.utils.log.Logger;
import com.wq.common.model.PushMessage;
import com.wq.common.quest.Config;
import com.wq.main.NavigatorActivity;
import com.wq.template.ShopTemplateDataActivity;
import com.wq.template.TeamTemplateDataActivity;

import cn.jpush.android.api.JPushInterface;

/**
 * 处理推送动作
 *
 * @author WQ 下午1:17:37
 */
public class DealPushAction {
    public DealPushAction(Context context, Intent intent) {
        String action = intent.getAction();
        if (action == null)
            return;
        printMsg(intent);
        switch (action) {
            case "cn.jpush.android.intent.NOTIFICATION_OPENED":
                // //通知消息打开
                clickHandler(context, getCustomMsg(intent));
                Logger.E("消息打开");
                break;
            case "cn.jpush.android.intent.REGISTRATION":// 注册成功
                String device_token = intent.getExtras().getString(
                        JPushInterface.EXTRA_REGISTRATION_ID);
                Config.putConfigInfo(context, JPushInterface.EXTRA_REGISTRATION_ID,device_token);
                Logger.D("REGISTRATION_ID  "+device_token);
                Config.putConfigInfo(context, "device_token", device_token);// 保存设备号
                break;
            case "cn.jpush.android.intent.MESSAGE_RECEIVED":// 接收到了自定义消息
                try {
                    receiverMsg(context, getCustomMsg(intent), getNofityID(intent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case "cn.jpush.android.intent.NOTIFICATION_RECEIVED":// 接收到了通知消息
                try {
                    receiverMsg(context, getCustomMsg(intent), getNofityID(intent));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 接收消息时的事务处理
     *
     * @param context
     * @param msgJson
     */
    private void receiverMsg(Context context, String msgJson, int notifiId) {
        Logger.E("接收到推送消息 :" + msgJson);
        openDetailsPage(context, msgJson);
    }

    /**
     * 通知栏点击事件事务处理
     *
     * @param context
     * @param msgJson
     */
    private void clickHandler(Context context, String msgJson) {
        openDetailsPage(context, msgJson);
//        if(PushMsg.isVaild(pushMsg)){

//        }
    }

    private void openDetailsPage(Context context, String msgJson) {
        PushMessage pushMsg= JsonDeal.json2Object(msgJson,PushMessage.class);
        if("1".equals(pushMsg.getType())){
            Intent mainIntent = new Intent(context, ShopTemplateDataActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra("template_id",pushMsg.getTemplate_id());
            context.startActivity(mainIntent);
        }else if("2".equals(pushMsg.getType())){
            Intent mainIntent = new Intent(context, TeamTemplateDataActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mainIntent.putExtra("template_id",pushMsg.getTemplate_id());
            context.startActivity(mainIntent);
        }else
        {
            Intent mainIntent = new Intent(context, NavigatorActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(mainIntent);
        }
    }

    public String getCustomMsg(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return null;
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        return extras;
    }

    public int getNofityID(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return 0;
        int notificationId = bundle
                .getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        return notificationId;
    }

    private void printMsg(Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle == null)
            return;
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String type = bundle.getString(JPushInterface.EXTRA_CONTENT_TYPE);
        Logger.E("消息内容:" + extras + "  " + message + "  " + "  " + type + "  "
                + intent.getAction());

    }
}
