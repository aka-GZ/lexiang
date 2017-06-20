package com.wq.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.utils.JsonDeal;
import com.sunrun.sunrunframwork.uibase.BaseActivity;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.EmptyDeal;
import com.sunrun.sunrunframwork.utils.Utils;
import com.wq.base.LBaseActivity;
import com.wq.common.App;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.LoginUtil;
import com.wq.project01.R;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GETVERIFICATIONCODE_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_USER_BACK_PASSWORD_CODE;
import static com.wq.login.other.LoginConfig.ACCOUNT;
import static com.wq.login.other.LoginConfig.PWD;


/**
 * @找回密码
 */

public class FonudPwdActivity extends LBaseActivity implements TextWatcher {
	@BindView(R.id.account) EditText account;
	@BindView(R.id.captcha) EditText captcha;
	@BindView(R.id.pwd) EditText pwd;

	@BindView(R.id.submit) Button submit;
	@BindView(R.id.getCaptcha) TextView getCaptcha;// 获取验证码
	int time = 60;
	String code_id;

	@Override
	protected void onCreate(Bundle arg0) {
		setContentView(R.layout.ui_found_pwd);
		super.onCreate(arg0);
		initViews();
	}

	protected void initViews() {
		account.addTextChangedListener(this);
		captcha.addTextChangedListener(this);
		submit.setEnabled(true);
	}

	/**
	 * 下一步
	 * 
	 * @param view
	 */
	@OnClick(R.id.submit)
	public void submit(View view) {
		UIUtils.showLoadDialog(this, "正在提交数据");
		BaseQuestStart.userBackPassword(this, account,pwd, captcha);
	}
//	public String getPhoneFormat(String phone){
//		return  phone.substring(3)+" "+phone.substring(3,8)+" "+phone.substring(8);
//		if(phone.length()<11)return phone;
//		StringBuilder stringBuilder=new StringBuilder(phone);
//		stringBuilder.insert(3,' ');
//		stringBuilder.insert(8,' ');
//		return stringBuilder.toString();
//	}

	@Override
	protected void onResume() {
		// 密码修改尚未完成的时候,返回该页面
		super.onResume();
	}

	public void nofityUpdate(int requestCode, BaseBean bean) {
		UIUtils.cancelLoadDialog();
		switch (requestCode) {

		case QUEST_USER_BACK_PASSWORD_CODE:
			UIUtils.shortM(bean.msg);
			if (bean.status == CODE_OK) {
				Intent data=new Intent();
				data.putExtra(ACCOUNT,account.getText().toString());
				data.putExtra(PWD,pwd.getText().toString());
				setResult(RESULT_OK,data);
				App.getInstance().closeAllActivity();
				LoginUtil.exitLogin(that);
//				StartIntent.startNewPwdAct(this,account.getText().toString(),code_id,getIntent().getStringExtra("Bj"));
			} else {
//				UIUtils.shortM(bean.msg);
			}
			break;
		case QUEST_GETVERIFICATIONCODE_CODE:
			UIUtils.shortM(bean.msg);// 显示提示信息
			if (bean.status != CODE_OK) {
				getCaptcha.setText(getString(R.string.getcaptcha));
				getCaptcha.setEnabled(true);
				AHandler.cancel(task);
			} else {
//				JSONObject jobj=JsonDeal.createJsonObj(bean.toString());
//				String codeStr = jobj.optString("code");
//				time=jobj.optInt("time",time);
//				code_id = codeStr;
//				captcha.setText(code_id);//暂时直接展示验证码
				//putLong("find_time", System.currentTimeMillis()).commit();
				//putString("find_code_id", code_id).commit();
				checkValid();
				runCountDown();
			}
			break;
		}
	};

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
	public void loadFaild(int requestCode, BaseBean bean) {
		super.loadFaild(requestCode, bean);
//		if (requestCode == QUEST_FIND_PASSWORD_CODE) {
//			getCaptcha.setEnabled(true);
//			getCaptcha.setText(getString(R.string.getcaptcha));
//			AHandler.cancel(task);
//		}
	}

	/**
	 * 获取验证码
	 * 
	 * @param view
	 */
	@OnClick(R.id.getCaptcha)
	public void getCaptcha(View view) {
//		if (EmptyDeal.isEmpy(account)) {
//			UIUtils.shortM("手机号不能为空");
//			return;
//		}
//		if (!Utils.isMobileNO(account.getText().toString())) {
//			UIUtils.shortM("手机号格式不正确");
//			return;
//		}
		if (task == null) {
			getCaptcha.setText("发送中..");
			getCaptcha.setEnabled(false);
			UIUtils.showLoadDialog(that, "获取验证码..");
			BaseQuestStart.captcha(this, account);
		}
	}

	private void checkValid() {
		submit.setEnabled(!EmptyDeal.isEmpy(account) && !EmptyDeal.isEmpy(captcha));
	}

	@Override
	protected void onDestroy() {
		AHandler.cancel(task);
		super.onDestroy();

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

		checkValid();
		// next.setEnabled(!EmptyDeal.isEmpy(account) &&
		// !EmptyDeal.isEmpy(captcha));
	}

	@Override
	public void afterTextChanged(Editable s) {}
}
