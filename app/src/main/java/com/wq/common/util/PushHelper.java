package com.wq.common.util;

import android.app.Notification;
import android.content.Context;

import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.log.Logger;
import com.wq.common.quest.Config;
import com.wq.project01.BuildConfig;
import com.wq.project01.R;

import java.util.Set;

import cn.jpush.android.api.BasicPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;


/**
 * 推送帮助类, 对基本方法做统一调度
 * 
 * @author WQ 下午1:54:11
 */
public class PushHelper {
	static  boolean isSetAlias=false;
	public static void stopPush(Context context) {
		if (!JPushInterface.isPushStopped(context)) {
			JPushInterface.stopPush(context);
		}
		isSetAlias=false;
		Logger.E("关闭推送");

	}

	/**
	 * 设置/更新别名
	 * 
	 * @param context
	 */
	public static void updateAlias(final Context context) {
		if (!Config.getLoginInfo().isValid() || isSetAlias) {
			Logger.E("别名设置:"+isSetAlias);
			return; 
		}
		// 消息推送测试

//		JPushInterface.setAlias(context, Config.getLoginInfo().getId(),
//				new TagAliasCallback() {
//
//					@Override
//					public void gotResult(int code, String arg1,
//							Set<String> arg2) {
//						switch (code) {
//						case 0:
//							isSetAlias=true;
//							Logger.E("设置别名成功");
//							// 建议这里往 SharePreference
//							// 里写一个成功设置的状态。成功设置一次后，以后不必再次设置了。
//							break;
//						case 6002:
//							Logger.E("设置超时");
//							// 延迟 60 秒来调用 Handler 设置别名
//							AHandler.runTask(new AHandler.Task() {
//								public void update() {
//									updateAlias(context);
//								};
//							}, 1000 * 60);
//							break;
//						default:
//							Logger.E("设置失败 " + code);
//						}
//					}
//				});
	}
	
	public static void setDefNotification(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				context);
		builder.statusBarDrawable = R.mipmap.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
				| Notification.FLAG_SHOW_LIGHTS; // 设置为自动消失和呼吸灯闪烁
		builder.notificationDefaults = Notification.DEFAULT_SOUND
				| Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS; // 设置为铃声、震动、呼吸灯闪烁都要
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
	
	public static void setNoVibNotification(Context context){
		BasicPushNotificationBuilder builder = new BasicPushNotificationBuilder(
				context);
		builder.statusBarDrawable = R.mipmap.ic_launcher;
		builder.notificationFlags = Notification.FLAG_AUTO_CANCEL
				| Notification.FLAG_SHOW_LIGHTS; // 设置为自动消失和呼吸灯闪烁
		builder.notificationDefaults = 
				 Notification.DEFAULT_LIGHTS; // 设置呼吸灯闪烁
		JPushInterface.setDefaultPushNotificationBuilder(builder);
	}
	
	public static void clearNofity(Context context,int notifyId){
		JPushInterface.clearNotificationById(context, notifyId);
	}
	public static void clearNofity(Context context){
//		JPushInterface.clearNotificationById(arg0, arg1);
	}

	public static void initPush(Context context) {
		//if (!Config.getLoginInfo().isValid()) { return; }

		if (JPushInterface.isPushStopped(context)) {
			JPushInterface.resumePush(context);
			Logger.E("推送恢复 ");
		} else {
			JPushInterface.setDebugMode(BuildConfig.DEBUG); // 设置开启日志,发布时请关闭日志
			JPushInterface.init(context);
			Logger.E("推送初始化 ");
		}

		updateAlias(context);
		setDefNotification(context);
	}

}
