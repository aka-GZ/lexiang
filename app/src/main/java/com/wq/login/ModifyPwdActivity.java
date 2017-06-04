package com.wq.login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uibase.BaseActivity;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.view.title.BaseTitleLayoutView;
import com.wq.common.App;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.quest.Config;
import com.wq.common.util.IntentUtil;
import com.wq.login.other.LoginConfig;
import com.wq.main.NavigatorActivity;
import com.wq.project01.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.sunrun.sunrunframwork.utils.EmptyDeal.empty;
import static com.wq.common.quest.BaseQuestConfig.QUEST_UPDATE_PASSWORD_CODE;

/**
 * 修改密码
 * Created by WQ on 2016/12/19.
 */

public class ModifyPwdActivity extends BaseActivity {
    @BindView(R.id.edit_old_pwd)
    EditText editOldPwd;
    @BindView(R.id.edit_new_pwd)
    EditText editNewPwd;
    @BindView(R.id.edit_confim_pwd)
    EditText editConfimPwd;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.title_layout_view)
    BaseTitleLayoutView titleLayoutView;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.ui_activity_modify_password);
        ButterKnife.bind(this);
        titleLayoutView.getRelRightArea().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startFonudPwdAct(that, Config.getLoginInfo().getPhone_no());
            }
        });
    }
    public void nofityUpdate(int requestCode,BaseBean bean){
        switch(requestCode){
            case QUEST_UPDATE_PASSWORD_CODE:
                UIUtils.shortM(bean);
                if(bean.status==1){
                    Config.putLoginInfo(null);
                    Config.putConfigInfo(that, LoginConfig.PWD,null);
                    IntentUtil.startLogin(that);
                    finish();
                    App.getInstance().closeActivitys(NavigatorActivity.class);
                }
                break;
        }
        super.nofityUpdate(requestCode,bean);
    }
    @OnClick(R.id.submit)
    public void submit(Button submit) {
        if(empty(editOldPwd,"原始密码不能为空")||empty(editNewPwd,"新密码不能为空"))return ;
        UIUtils.showLoadDialog(that,"密码修改中..");
        BaseQuestStart.updateUserPassword(this,editOldPwd,editNewPwd,editConfimPwd);
    }
}
