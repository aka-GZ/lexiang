package com.wq.template;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.sunrun.sunrunframwork.adapter.ViewHodler;
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uibase.PagingActivity;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
import com.wq.base.LPageActivity;
import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.model.LoginInfo;
import com.wq.common.model.TeamTemplateListObj;
import com.wq.common.quest.BaseQuestConfig;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wq.common.quest.BaseQuestConfig.QUEST_LOGIN_CODE;
import static com.wq.project01.R.id.img_icon;

/**
 * Created by weiquan on 2017/6/2.0
 */

public class TeamTemplateListActivity extends LPageActivity {

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
        setPullListener(refreshLayout);
        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View dialogView = View.inflate(that,R.layout.dialog_send_info,null);
                Dialog dialog= UIUtils.createDialog(that,dialogView);
            }
        });
    }

    @Override
    public void loadData(int i) {
        ArrayList list=new ArrayList();
        list.addAll(Arrays.asList(new String[10]));
        setDataToView(list,refreshLayout.getRefreshableView());
        //group_id 团队ID  ,  firstRow 开始记录数   , listRows 每页显示数量
        String group_id = "1";
        String firstRow = i+"";
        String listRows = "10";
        BaseQuestStart.getTeamTemplateList(this,group_id,firstRow,listRows);
    }


    public void nofityUpdate(int requestCode, BaseBean bean) {
        switch (requestCode) {
            case BaseQuestConfig.QUEST_GET_TEAM_TEMPLATE_LIST_CODE:
                //log 设置 tag为NetServer 可以查看请求情况
                if (bean.status == 200) {
                    List<TeamTemplateListObj> info=bean.Data();//获取数据内容
                    setDataToView(info,refreshLayout.getRefreshableView());
                }
                break;
        }
        super.nofityUpdate(requestCode, bean);
    }

    @Override
    public BaseAdapter getAdapter(List list) {
        return new ViewHolderAdapter<TeamTemplateListObj>(that,list,R.layout.item_team_template) {

            @Override
            public void fillView(ViewHodler viewHodler, TeamTemplateListObj o, int i) {
                GlideMediaLoader.load(mContext,viewHodler.getView(R.id.img_icon),o.getTemplate_cover_img_url());
                viewHodler.setText(R.id.tv_titlle,o.getTemplate_name());
                viewHodler.setText(R.id.tv_time,o.getTemplate_add_time());


            }
        };
    }
}
