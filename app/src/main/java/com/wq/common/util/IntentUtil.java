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
import com.wq.main.TemplateListActivity;
import com.wq.template.SelectPeopleActivity;
import com.wq.template.ShopTemplateDataActivity;
import com.wq.template.TemplateDataActivity;

import org.jetbrains.annotations.Nullable;

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
     * 取值getSession().getBean("ids",new TypeToken<List<String>(){})
     * getSession().getString("names");
     */
    public static void startSlectPeopleActivity(Activity context,int requestCode){
        Intent intent=new Intent(context, SelectPeopleActivity.class);
        context.startActivityForResult(intent,requestCode);
    }



    /**
     * 九宫格市场模板列表
     * @param that
     * @param url
     */
    public static void startTemplateListActivity( Activity that, @Nullable String url,String keywords,String title) {
        Intent intent=new Intent(that,TemplateListActivity.class);
        intent.putExtra("url",url);
        intent.putExtra("keywords",keywords);
        intent.putExtra("title",title);
        that.startActivity(intent);
    }


    /**
     * 模板详情
     * @param that
     * @param template_name
     * @param template_id
     */
    public static void startTemplateDataActivity(@Nullable Activity that, @Nullable String template_name, @Nullable String template_id) {
        Intent intent=new Intent(that,ShopTemplateDataActivity.class);
        intent.putExtra("template_name",template_name);
        intent.putExtra("template_id",template_id);
        that.startActivity(intent);
    }
    /**
     * 我的模板详情
     * @param that
     * @param template_name
     * @param template_id
     */
    public static void startMyTemplateDataActivity(@Nullable Activity that, @Nullable String template_name, @Nullable String template_id) {
        Intent intent=new Intent(that,TemplateDataActivity.class);
        intent.putExtra("template_name",template_name);
        intent.putExtra("template_id",template_id);
        that.startActivity(intent);
    }
}
