package com.wq.common.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.sunrun.sunrunframwork.uibase.BaseActivity;
import com.sunrun.sunrunframwork.utils.BaseStartIntent;
import com.wq.login.FonudPwdActivity;
import com.wq.login.LoginActivity;
import com.wq.login.RegisterActivity;
import com.wq.main.NavigatorActivity;
import com.wq.template.SelectPeopleActivity;

/**
 * Created by weiquan on 2017/6/2.
 */

public class IntentUtil extends BaseStartIntent {
    //跳转到注册页面
    public static void startRegisterAct(Activity loginActivity, String account) {
        Intent intent = new Intent(loginActivity, RegisterActivity.class);
//        intent.putExtra("isReLogin", isReLogin);
        intent.putExtra("account", account);
        loginActivity.startActivityForResult(intent, 0x0011);
    }

    //找回密码
    public static void startFonudPwdAct(Activity loginActivity, String account) {
        Intent intent = new Intent(loginActivity, FonudPwdActivity.class);
//        intent.putExtra("Bj", "登录密码");
        intent.putExtra("account", account);
        loginActivity.startActivityForResult(intent, 0x0022);
    }

    public static void startNewPwdAct(Activity fonudPwdActivity, String mobile, String code_id, String bj) {
//        Intent intent = new Intent(fonudPwdActivity, NewPwdActivity.class);
//        intent.putExtra("mobile", mobile);
//        intent.putExtra("code_id", code_id);
//        intent.putExtra("Bj", bj);
//        fonudPwdActivity.startActivityForResult(intent,0x0022);
    }

    //跳到登录
    public static void startLogin(Context that) {
        startLogin(that,false);
    }
    //跳到登录
    public static void startLogin(Context that,boolean isOrtherLogin) {
        Intent intent=new Intent(that, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("isOrtherLogin",isOrtherLogin);
        that.startActivity(intent);
    }

    public static void startMainActivity(BaseActivity that) {
        Intent intent=new Intent(that, NavigatorActivity.class);
        that.startActivity(intent);
    }

    /**
     *
     * 选择提醒谁看
     * @param context
     * @param requestCode
     */
    public static void startSlectPeopleActivity(Activity context,int requestCode){
        Intent intent=new Intent(context, SelectPeopleActivity.class);
        context.startActivityForResult(intent,requestCode);
    }
}
