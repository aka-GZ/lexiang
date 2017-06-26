package com.wq.jpush;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.sunrun.sunrunframwork.utils.log.Logger;

import cn.jpush.android.api.JPushInterface;


/**
 * @author wok
 * 
 *         jpush自定义消息通知
 * 
 *         如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 * 
 **/
public class MyPushReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent != null && intent.getAction() != null) {
			new DealPushAction(context, intent);
		}
		Bundle bundle = intent.getExtras();
		Log.e("MyPushReceiver", "intent.getAction() - " + intent.getAction());

		if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {

			// SDK 向 JPush Server 注册所得到的注册 全局唯一的 ID ,可以通过此 ID 向对应的客户端发送消息和通知。

		} 
		else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {

			// 收到了自定义消息
//			System.out.println("收到了自定义通知");
			String title = bundle.getString(JPushInterface.EXTRA_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);

			Log.e("收到了自定义通知", title + "---" + message);
//			// 自定义消息不会展示在通知栏，完全要开发者写代码去处理
//			Builder dialog = new AlertDialog.Builder(context);
//			dialog.setTitle(title);
//			dialog.setMessage(message);
//			dialog.setPositiveButton("确定这是自定义的通知", new OnClickListener() {
//
//				public void onClick(DialogInterface arg0, int arg1) {
//
//				}
//			});
//			dialog.create().show();

		} 
		else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {

			// 收到了通知
			// 在这里可以做些统计，或者做些其他工作

			String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_ALERT);
			String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);

			
			Log.e("收到了通知", file + "---" + title + "---" + message);
			
//			MainActivity.badgeNum++;
//			Intent i = new Intent(MainActivity.ACTION_UPDATEUI);
//			Bundle bd = new Bundle();
//			bd.putInt("badgeNum", MainActivity.badgeNum);
//			i.putExtras(bd);
//			context.sendBroadcast(i);

			
			// 自定义打开的界面
//			Intent i = new Intent(context, JpushMsgActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			i.putExtra("msgid", file);
//			i.putExtra("msg", message);
//			context.startActivity(i);
						
			

		} 
		else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {

			// 用户点击打开了通知
			String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
			String message = bundle.getString(JPushInterface.EXTRA_ALERT);
			String file = bundle.getString(JPushInterface.EXTRA_MSG_ID);

			
			Log.e("点击通知栏打开了通知", file + "---" + title + "---" + message);
			
//			if (MainActivity.badgeNum >= 1) {
//				MainActivity.badgeNum--;
//				Intent i = new Intent(MainActivity.ACTION_UPDATEUI);
//				Bundle bd = new Bundle();
//				bd.putInt("badgeNum", MainActivity.badgeNum);
//				i.putExtras(bd);
//				context.sendBroadcast(i);
//			}
			

			
			
			// 自定义打开的界面
//			Intent i = new Intent(context, JpushMsgActivity.class);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			i.putExtra("msgid", file);
//			i.putExtra("msg", message);
//			context.startActivity(i);

		} else {
			Log.e("MyPushReceiver","Unhandled intent.getAction() - " + intent.getAction());
		}

	}

}
