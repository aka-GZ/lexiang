package com.wq.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnLayoutChangeListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uibase.BaseActivity;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.log.Logger;
import com.sunrun.sunrunframwork.view.title.BaseTitleLayoutView;
import com.wq.base.LBaseActivity;
import com.wq.common.model.LoginInfo;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.quest.Config;
import com.wq.common.util.IntentUtil;
import com.wq.common.widget.TitleBar;
import com.wq.login.other.LoginConfig;
import com.wq.project01.R;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

import static com.sunrun.sunrunframwork.utils.EmptyDeal.empty;
import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_LOGIN_CODE;

/**
 * @author WQ 2015年9月29日
 * @登录界面
 */
public class LoginActivity extends LBaseActivity implements TextWatcher, LoginConfig {
    @BindView(R.id.account)
    EditText account;// 账号
    @BindView(R.id.pwd)
    EditText pwd;// 密码
    @BindView(R.id.remberPwd)
    CheckBox remberPwd;//记住密码,废弃
    @BindView(R.id.login)
    Button login;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.content)
    LinearLayout content;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    private String accountStr = null, pwdStr = null;
    boolean isCachePwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ui_login);
        super.onCreate(savedInstanceState);
    }

    /**
     * 判断是否登录
     *
     * @param act
     * @param turnLogin 是否判断完毕后跳转到登录页面
     * @return
     */
    public static boolean isLogin(Context act, boolean turnLogin) {
        if (Config.getLoginInfo().isValid()) {
            return true;
        }
        if (turnLogin) {
            // UIUtils.shortM("请先登录");
            Intent intent = new Intent(act, LoginActivity.class);
            intent.putExtra("isReLogin", true);
            act.startActivity(intent);
        }
        return false;
    }

    protected void initView() {
//		 setStatusBarTintColor(getColors(R.color.main));// 设置状态栏颜色
        // 加载保存的用户名和密码
        accountStr = Config.getConfigInfo(this, ACCOUNT,
                null);
        if(accountStr!=null) {
            account.setText(accountStr);//
        }
        pwdStr = Config.getConfigInfo(this, PWD, null);
        if (!empty(accountStr) && !empty(pwdStr)) {
            isCachePwd = true;
            remberPwd.setChecked(true);
            // pwd.setText("**************");
          //  pwd.setText(pwdStr);
        }
        isVaild();
        pwd.addTextChangedListener(this);
        account.addTextChangedListener(this);
        content.addOnLayoutChangeListener(new OnLayoutChangeListener() {

            @Override
            public void onLayoutChange(View v, int left, int top, int right,
                                       int bottom, int oldLeft, int oldTop, int oldRight,
                                       int oldBottom) {
                AHandler.runTask(new AHandler.Task() {
                    @Override
                    public void update() {
                        scrollview.scrollTo(0, 1000);
                    }
                }, 50);

            }
        });
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isVaild();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (isCachePwd) {
            s.clear();
            isCachePwd = false;
        }
        pwdStr = null;
    }

    @OnClick(R.id.regist)
    public void regist(View view) {
        // 注册
        IntentUtil.startRegisterAct(this, account.getText().toString());
    }

    @OnClick(R.id.forgetpassword)
    public void forgetpassword(View view) {
        // 忘记密码
        IntentUtil.startFonudPwdAct(this, account.getText().toString());
    }

    @OnClick(R.id.login)
    public void login(View view) {
        Logger.D("登录");
        if (check()) {
            UIUtils.showLoadDialog(this, "登录中..");
            getIntent().putExtra("is_other", false);
            BaseQuestStart.login(this, accountStr, pwdStr, JPushInterface.getRegistrationID(this));
            // 保存账户信息
            Config.putConfigInfo(this, ACCOUNT, accountStr);
        }

    }

    void checkLoginStatus(BaseBean bean) {
        LoginInfo info = (LoginInfo) bean.Data();
        if (info != null) {
            Config.putLoginInfo(info);
            remberPwd(remberPwd.isChecked());
            IntentUtil.startMainActivity(that);
            finish();

        }
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {

            case QUEST_LOGIN_CODE:
                UIUtils.shortM(bean);
                if (bean.status == CODE_OK) {
                    checkLoginStatus(bean);
                }
                break;
        }
    }


    /**
     * 检查按钮是否可用,
     *
     * @return
     */
    boolean isVaild() {
        boolean flag = false;
        login.setEnabled(!(empty(account) && empty(pwd)));
        return flag;
    }

    /**
     * 输入验证
     *
     * @return
     */
    private boolean check() {
        if (empty(account)) {
            UIUtils.shortM("手机号不能为空");
            return false;
        }
        if (empty(pwd)) {
            UIUtils.shortM("密码不能为空");
            return false;
        }
        accountStr = account.getText().toString();
        if (!isCachePwd || empty(pwdStr))// 如果密码或账户为空//尝试从文本框中读取
            pwdStr = pwd.getText().toString();
        // if (!isCachePwd)// 非缓存模式,对密码进行加密操作
        // pwdStr = Utils.getMD5(pwdStr);
        return true;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            account.setText(data.getStringExtra(ACCOUNT));
           // pwd.setText(pwdStr = data.getStringExtra(PWD));
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void remberPwd(boolean isRemberpwd) {
        // 保存密码
        Config.putConfigInfo(this, ACCOUNT, accountStr);
        Config.putConfigInfo(this, PWD, isRemberpwd ? pwdStr : null);
    }


}
