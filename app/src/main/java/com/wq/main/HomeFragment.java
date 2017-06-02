package com.wq.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wq.base.LBaseFragment;
import com.wq.common.util.IntentUtil;
import com.wq.project01.R;
import com.wq.template.AddTemplateActivity;

/**
 * 首页
 * Created by WQ on 2017/6/1.
 */

public class HomeFragment extends LBaseFragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //页面创建
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentUtil.startActivity(that, AddTemplateActivity.class);
            }
        });
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }
}
