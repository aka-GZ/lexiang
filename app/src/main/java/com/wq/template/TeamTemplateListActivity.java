package com.wq.template;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;

import com.sunrun.sunrunframwork.adapter.ViewHodler;
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter;
import com.sunrun.sunrunframwork.uibase.PagingActivity;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
import com.wq.base.LPageActivity;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

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
                View dialogView
                        =View.inflate(that,R.layout.dialog_send_info,null);
                Dialog dialog= UIUtils.createDialog(that,dialogView);
            }
        });
    }

    @Override
    public void loadData(int i) {
        ArrayList list=new ArrayList();
        list.addAll(Arrays.asList(new String[10]));
        setDataToView(list,refreshLayout.getRefreshableView());
    }

    @Override
    public BaseAdapter getAdapter(List list) {
        return new ViewHolderAdapter(that,list,R.layout.item_team_template) {

            @Override
            public void fillView(ViewHodler viewHodler, Object o, int i) {

            }
        };
    }
}
