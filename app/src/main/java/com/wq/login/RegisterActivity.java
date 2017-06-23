package com.wq.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.EmptyDeal;
import com.sunrun.sunrunframwork.utils.Utils;
import com.wq.base.LBaseActivity;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.quest.Config;
import com.wq.common.util.IntentUtil;
import com.wq.common.widget.TitleBar;
import com.wq.login.other.LoginConfig;
import com.wq.project01.R;

import java.util.Timer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sunrun.sunrunframwork.utils.EmptyDeal.size;
import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GETVERIFICATIONCODE_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_REGISTER_CODE;


/**
 * 注册页
 */
public class RegisterActivity extends LBaseActivity implements TextWatcher, LoginConfig {
    @BindView(R.id.account)
    EditText account;// 账号
    @BindView(R.id.pwd)
    EditText pwd;// 密码
    @BindView(R.id.pwd2)
    EditText pwd2;// 确认密码密码
    @BindView(R.id.captcha)
    EditText captcha;// 验证码
    @BindView(R.id.getCaptcha)
    TextView getCaptcha;// 获取验证码
    @BindView(R.id.readAgreement)
    CheckBox readAgreement;
    @BindView(R.id.showPwd)
    View showPwd;
    @BindView(R.id.register)
    Button register;
    @BindView(R.id.login)
    View login;
    @BindView(R.id.agreement)
    View agreement;
    boolean isShowPwd = false;
    Timer timer;
    int time = 60;
    String code_id;
    //  boolean isReLogin;// 来自个人中心
    int type = 0;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.editUsername)
    EditText editUsername;
    @BindView(R.id.editCompany)
    EditText editCompany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ui_register);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initNetServer();
        type = getIntent().getIntExtra("type", 0);
        account.setText(getIntent().getStringExtra("account"));
        register.setEnabled(true);
        int old_time = (int) ((System.currentTimeMillis() - getSession().getLong("register_time")) / 1000);
        if (old_time < 60) {
            time = time - old_time;
            code_id = "has";
            runCountDown();
        }
        account.addTextChangedListener(this);
        captcha.addTextChangedListener(this);
        pwd.addTextChangedListener(this);
        pwd2.addTextChangedListener(this);
    }


    /**
     * 获取验证码
     *
     * @param view
     */
    @OnClick(R.id.getCaptcha)
    public void getCaptcha(View view) {
        if (size(account) != 11) {
            UIUtils.shortM("手机号码不正确");
            return;
        }
        if (task == null) {
            getCaptcha.setText("发送中..");
            getCaptcha.setEnabled(false);
            UIUtils.showLoadDialog(that, "获取验证码..");
            BaseQuestStart.captcha(this, account);
        }
    }

    /**
     * 开启倒计时
     */
    AHandler.Task task;

    private void runCountDown() {
        AHandler.runTask(task = new AHandler.Task() {
            @Override
            public void update() {
                if (time <= 0) {
                    task.cancel();
                } else {
                    getCaptcha.setEnabled(false);
                    getCaptcha.setText(String.format("重新发送(%ds)", time--));
                }
            }

            @Override
            public boolean cancel() {
                boolean flag = super.cancel();
                getCaptcha.setEnabled(true);
                getCaptcha.setText(getString(R.string.getcaptcha));
                time = 60;
                task = null;
                return flag;
            }

        }, 0, 1000);
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case QUEST_REGISTER_CODE:

                if (bean.status == CODE_OK) {
                    Intent intent = new Intent();
                    intent.putExtra(ACCOUNT, account.getText().toString());
                    intent.putExtra(PWD, pwd.getText().toString());
                    Config.putConfigInfo(this, ACCOUNT, account.getText().toString());
                    Config.putConfigInfo(this, PWD, pwd.getText().toString());
                    setResult(RESULT_OK, intent);
                    finish();
                    UIUtils.shortM(bean.msg);
                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;
            case QUEST_GETVERIFICATIONCODE_CODE:
                UIUtils.shortM(bean.msg);
                if (bean.status != CODE_OK) {
                    getCaptcha.setText(getString(R.string.getcaptcha));
                    getCaptcha.setEnabled(true);
                    AHandler.cancel(task);
                } else {
//                    JSONObject jobj=JsonDeal.createJsonObj(bean.toString());
//                    String codeStr = jobj.optString("code");
//                    time=jobj.optInt("time",time);
//                    captcha.setText(codeStr);
                    code_id = "has";
                    isVaild();
                    runCountDown();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void loadFaild(int requestCode, BaseBean bean) {
        super.loadFaild(requestCode, bean);
        if (requestCode == QUEST_GETVERIFICATIONCODE_CODE) {
            getCaptcha.setEnabled(true);
            getCaptcha.setText(getString(R.string.getcaptcha));
            AHandler.cancel(task);
        }
    }

    @OnClick(R.id.register)
    public void register(View view) {

        if (EmptyDeal.isEmpy(account)) {
            UIUtils.shortM("手机号不能为空");
            return;
        }
//        if (!Utils.isMobileNO(account.getText().toString())) {
//            UIUtils.shortM("手机号格式不正确");
//            return;
//        }

        if (EmptyDeal.isEmpy(code_id)) {
            UIUtils.shortM("请先获取验证码");
            return;
        }
        if (EmptyDeal.isEmpy(pwd)) {
            UIUtils.shortM("密码不能为空");
            return;
        }
        if (!pwd.getText().toString().equals(pwd2.getText().toString())) {
            UIUtils.shortM("两次输入的密码不一致");
            return;
        }
        UIUtils.showLoadDialog(that, "注册中..");
        // 注册
        BaseQuestStart.register(this, account, pwd.getText().toString(), captcha, editUsername,editCompany);
    }

    boolean isVaild() {
        boolean flag = false;
        register.setEnabled(flag = Utils.isMobileNO(account.getText().toString()) && !EmptyDeal.isEmpy(pwd.getText().toString())
//                &&
//                !EmptyDeal.isEmpy(captcha)
                        &&
                        !EmptyDeal.isEmpy(pwd) && !EmptyDeal.isEmpy(pwd2)
//                && readAgreement.isChecked()
        );
        return flag;
    }

    public void showPwd(View view) {
        // 显示密码
        if (isShowPwd) {
            isShowPwd = false;
            pwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else {
            isShowPwd = true;
            pwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
        }
    }

    @OnClick(R.id.login)
    public void login(View view) {
        // 登录
        IntentUtil.startLogin(that);
        finish();
    }

    @OnClick(R.id.forgetpassword)
    public void forgetpassword(View view) {
        // 忘记密码
        IntentUtil.startFonudPwdAct(this, account.getText().toString());
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isVaild();
    }

    @Override
    public void afterTextChanged(Editable s) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onDestroy() {
        AHandler.cancel(task);
        super.onDestroy();
    }
}
