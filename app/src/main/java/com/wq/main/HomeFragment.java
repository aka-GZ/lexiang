package com.wq.main;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sunrun.sunrunframwork.bean.BaseBean;
import com.wq.base.LBaseFragment;
import com.wq.common.model.LoginInfo;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.IntentUtil;
import com.wq.project01.R;
import com.wq.template.AddTemplateActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wq.common.quest.BaseQuestConfig.QUEST_LOGIN_CODE;

/**
 * 首页
 * Created by WQ on 2017/6/1.
 */

public class HomeFragment extends LBaseFragment {

    @BindView(R.id.tv_content)
    TextView tvContent;

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
        BaseQuestStart.login(this, "15916035572", "25d55ad283aa400af464c76d713c07ad");
    }

    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case QUEST_LOGIN_CODE:
                //log 设置 tag为NetServer 可以查看请求情况
                tvContent.setText(bean.toString());
                if (bean.status == 200) {
                    LoginInfo info=bean.Data();//获取数据内容
                }
                break;
        }
        super.nofityUpdate(requestCode, bean);
    }

    @Override
    public int getLayoutRes() {
        return R.layout.fragment_home;
    }

}
