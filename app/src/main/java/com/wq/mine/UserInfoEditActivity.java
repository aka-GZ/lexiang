package com.wq.mine;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.wq.base.LBaseActivity;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;


/**
 * 通用个人信息编辑
 */
public class UserInfoEditActivity extends LBaseActivity {

    private TitleBar titleBar;
    private EditText editView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_single_edit);
        setResult(RESULT_CANCELED);
        initTitleBar();
        initParams();
        initSaveAction();
//        editView.setTextHint("请输入"+getIntent().getStringExtra(TAG_TITLE));
    }

    private void initSaveAction() {
        this.titleBar.setRightAction(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillContentToIntent();
            }
        });
    }

    private void fillContentToIntent() {
        final String content = editView.getText().toString().trim();
        if (TextUtils.isEmpty(content) && false) {
            ToastUtils.shortToast("不能保存空的内容哦");
            return;
        }
        Intent intent = getIntent();
        intent.putExtra(TAG_CONTENT, content);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void initParams() {
        Intent intent = getIntent();
        final String title = intent.getStringExtra(TAG_TITLE);
        final String content = intent.getStringExtra(TAG_CONTENT);
        titleBar.setTitle(title);
        editView.setText(content);
    }

    private void initTitleBar() {
        titleBar = (TitleBar) findViewById(R.id.titleBar);
        editView = (EditText) findViewById(R.id.editContent);
    }

    public static final String TAG_TITLE = "TAG_TITLE";
    public static final String TAG_TAG = "TAG_TAG";
    public static final String TAG_CONTENT = "TAG_CONTENT";
    public static void launch(Activity context,String title, int requestCode, String content) {
        Intent it = new Intent(context, UserInfoEditActivity.class);
        it.putExtra(TAG_TITLE, title);
        it.putExtra(TAG_CONTENT, content);
        context.startActivityForResult(it,requestCode);
    }




}
