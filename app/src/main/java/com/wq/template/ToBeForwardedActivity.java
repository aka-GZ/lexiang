package com.wq.template;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.sunrun.sunrunframwork.adapter.ViewHodler;
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.http.utils.DateUtil;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
import com.wq.base.LBaseActivity;
import com.wq.base.LPageActivity;
import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.model.GroupListObj;
import com.wq.common.model.TeamTemplateListObj;
import com.wq.common.model.ToBeForwardedObj;
import com.wq.common.quest.BaseQuestConfig;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.IntentUtil;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Zheng on 2017/7/2.
 */

public class ToBeForwardedActivity extends LPageActivity {

    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.refresh_layout)
    PullToRefreshListView refreshLayout;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_team_template_list);
        ButterKnife.bind(this);
        setPullListener(refreshLayout);
//        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
////                IntentUtil.startMyTemplateDataActivity(that, getListData().get(index-1).template_name, getListData().get(index-1).template_id);
//                IntentUtil.startTemplateDataActivity(that, getListData().get(index - 1).template_name, getListData().get(index - 1).template_id);
//            }
//        });
    }

    @Override
    public void loadData(int i) {

        BaseQuestStart.getForwardTemplateRemind(this);
    }

    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case BaseQuestConfig.QUEST_GET_FORWARD_TEMPLATE_REMIND_CODE:
                //log 设置 tag为NetServer 可以查看请求情况
                if (bean.status == 200) {
                    List<ToBeForwardedObj> list = bean.Data();//获取数据内容

                    if (list != null && !list.isEmpty() && list.get(0) != null) {
                        setDataToView(list,refreshLayout.getRefreshableView());
                    } else {
                        ToastUtils.shortToast("暂无团队信息");
                    }
                }
                break;
        }
        super.nofityUpdate(requestCode, bean);
    }

    @Override
    public BaseAdapter getAdapter(List list) {
        return new ViewHolderAdapter<ToBeForwardedObj>(that, list, R.layout.item_to_be_forwarded) {

            public void fillView(ViewHodler viewHodler, final ToBeForwardedObj o, int i) {
                viewHodler.setText(R.id.tv_name, o.getTemplate_name());
                viewHodler.setText(R.id.tv_time, DateUtil.getTimeText(o.getAdd_time()));
                viewHodler.setClickListener(R.id.ll_forward, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent mainIntent = new Intent(that, TeamTemplateDataActivity.class);
//                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mainIntent.putExtra("template_id",o.getTemplate_id());
                        that.startActivity(mainIntent);
                    }
                });
            }
        };

    }


}
