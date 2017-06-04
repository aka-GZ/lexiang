package com.wq.template;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.sunrun.sunrunframwork.adapter.ViewHodler;
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
import com.wq.base.LPageActivity;
import com.wq.common.util.ChooserHelper;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by weiquan on 2017/6/2.0
 */

public class UseHistoryListActivity extends LPageActivity {


    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.start_time)
    TextView startTime;
    @BindView(R.id.end_time)
    TextView endTime;
    @BindView(R.id.btn_search)
    Button btnSearch;
    @BindView(R.id.refresh_layout)
    PullToRefreshListView refreshLayout;
    ChooserHelper startTimeChooser=new ChooserHelper();
    ChooserHelper endTimeChooser=new ChooserHelper();
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setContentView(R.layout.activity_use_history_list);
        ButterKnife.bind(this);
        setPullListener(refreshLayout);
        refreshLayout.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                View dialogView
                        = View.inflate(that, R.layout.dialog_send_info, null);
                Dialog dialog = UIUtils.createDialog(that, dialogView);
            }
        });
    }

    @Override
    public void loadData(int i) {
        ArrayList list = new ArrayList();
        list.addAll(Arrays.asList(new String[10]));
        setDataToView(list, refreshLayout.getRefreshableView());
    }

    @Override
    public BaseAdapter getAdapter(List list) {
        return new ViewHolderAdapter(that, list, R.layout.item_use_history) {

            @Override
            public void fillView(ViewHodler hodler, Object o, int i) {
                hodler.setVisibility(R.id.lay_lab,i%3==0);

            }
        };
    }

    @OnClick({R.id.start_time, R.id.end_time, R.id.btn_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.start_time:
                startTimeChooser.showDateChooser(that, startTime, new WheelDatePicker.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(WheelDatePicker picker, Date date) {

                    }
                });
                break;
            case R.id.end_time:
                endTimeChooser.showDateChooser(that, endTime, new WheelDatePicker.OnDateSelectedListener() {
                    @Override
                    public void onDateSelected(WheelDatePicker picker, Date date) {

                    }
                });
                break;
            case R.id.btn_search:
                break;
        }
    }
}
