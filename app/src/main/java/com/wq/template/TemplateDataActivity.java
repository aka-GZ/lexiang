package com.wq.template;

import android.os.Bundle;

import com.wq.base.LBaseActivity;
import com.wq.project01.R;

/**
 * Created by Zheng on 2017/6/11.
 */

public class TemplateDataActivity extends LBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ui_activity_templatedata);
        super.onCreate(savedInstanceState);

        getdata();


    }

    private void getdata() {
        String name = getIntent().getStringExtra("");
        String id = getIntent().getStringExtra("");
    }
}
