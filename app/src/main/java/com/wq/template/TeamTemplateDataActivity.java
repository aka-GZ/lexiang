package com.wq.template;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uiutils.PictureShow;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.base.LBaseActivity;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.model.TemplateStatisticsObj;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.ChooserHelper;
import com.wq.common.util.ShareHelper;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;
import com.wq.template.adapters.TemplateDataAdapter;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DateTimePicker;

import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEAM_TEMPLATE_VIEW_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEMPLATE_STATISTICS_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_SET_TEMPLATE_REMIND_CODE;

/**
 * Created by Zheng on 2017/6/11.
 */

public class TeamTemplateDataActivity extends LBaseActivity {

    String template_name;
    String template_id;
    @BindView(R.id.titlebar)
    TitleBar titlebar;
    @BindView(R.id.templatedata_tv)
    TextView templatedataTv;
    @BindView(R.id.templatedata_gv)
    GridView templatedataGv;
    @BindView(R.id.templatedata_shared_tv)
    TextView templatedataSharedTv;

    ShareHelper shareHelper = new ShareHelper(this);
    @BindView(R.id.templatedata_remind_tv)
    TextView templatedataRemindTv;
    @BindView(R.id.templatedata_inform_tv)
    TextView templatedataInformTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.ui_activity_templatedata);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        getdata();
        init();


    }

    private void init() {
        titlebar.setTitle("素材市场");
    }

    private void getdata() {
        template_name = getIntent().getStringExtra("template_name");
        template_id = getIntent().getStringExtra("template_id");
        /**
         * 获取我所在团队的九宫格模板详细数据
         * API参数传入方式: POST
         * 传入JSON格式: {"group_id":"1","template_id":"5"}
         * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"template_id":"3","template_name":"u6d4bu8bd5777","class_id":"-1","template_cover_img_url":"http://localhost:81/Public/Uploads/Model/Cube/2017-05-26/7sjhu0tyhGYkFjnM.jpeg","template_add_time":"2017-05-26 05:38:51","uid":"43"}]}
         * API_URL_本地: http://localhost:81/api.php/Cube/getTeamTemplateView
         * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getTeamTemplateView
         * @param group_id 团队ID
         * @param template_id 模板id
         * @return code 200->成功 3001->template_id参数为空 3002->模板不存在 3009->当前账号未在此团队中
         * @return template_name->九宫格模板名称标题
         * @return template_content->模板内容
         * @return template_cover_img_url->模板封面图片路径
         * @return is_open->模板是否公开 -1不公开 1公开
         * @return is_member_free->是否会员是会员模板 -1不是 1是
         * @return template_id->模板ID
         * @return template_add_time->模板添加时间
         * @return imgList->九宫格模板图片路径list
         */
        BaseQuestStart.getTeamTemplateView(this, getSession().getString("group_id"), template_id);
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {

        switch (requestCode) {
            case QUEST_GET_TEAM_TEMPLATE_VIEW_CODE:
                if (bean.status == CODE_OK) {

                    final TemplateDataObj obj = bean.Data();//获取数据内容

                    titlebar.setTitle("团队模板-" + obj.getTemplate_name());
                    templatedataTv.setText("" + obj.getTemplate_content());
                    TemplateDataAdapter adapter = new TemplateDataAdapter(TeamTemplateDataActivity.this, obj.getImgList());
                    templatedataGv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    templatedataGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            PictureShow pictureShow=new PictureShow(that);
                            pictureShow.setArgment(obj.getImgList(),i);
                            pictureShow.show();
                        }
                    });
                    shareHelper.saveShareImage(obj, false);
                    templatedataSharedTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //  ToastUtils.shortToast("分享");

                            shareHelper.saveShareImage(obj, true);
                        }
                    });


                    templatedataRemindTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            onYearMonthDayTimePicker();
//                            new ChooserHelper().showDateChooser(TeamTemplateDataActivity.this, templatedataRemindTv, new WheelDatePicker.OnDateSelectedListener() {
//                                @Override
//                                public void onDateSelected(WheelDatePicker picker, Date date) {
//                                    String remind_time = templatedataRemindTv.getText().toString();
//                                    Log.e("remind_time = " , ""+ remind_time);
//                                    BaseQuestStart.setTemplateRemind(TeamTemplateDataActivity.this, template_id , remind_time);
//                                }
//                            });

                        }
                    });
                    templatedataInformTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            BaseQuestStart.getTemplateStatistics(TeamTemplateDataActivity.this, template_id);

                        }
                    });


                } else if (bean.status == 3003) {


                    UIUtils.shortM(bean.msg);
                    finish();

                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;

            case QUEST_SET_TEMPLATE_REMIND_CODE:
                if (bean.status == CODE_OK) {

                    UIUtils.shortM(bean.msg);

                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;

            case QUEST_GET_TEMPLATE_STATISTICS_CODE:
                if (bean.status == CODE_OK) {

                    TemplateStatisticsObj obj = bean.Data();//获取数据内容
                    new AlertDialog.Builder(TeamTemplateDataActivity.this)
                            .setMessage("使用次数:  " + obj.getUse_num() + "\n转发次数:  " + obj.getForwarding_times())
                            .setPositiveButton("确定" , new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setCancelable(true)
                            .create().show();

                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;
        }
    }

    public void onYearMonthDayTimePicker() {

        DateTimePicker picker = new DateTimePicker(this, DateTimePicker.HOUR_24);
        Calendar calendar=Calendar.getInstance();
        picker.setDateRangeStart(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)+1, calendar.get(Calendar.DAY_OF_MONTH));

        picker.setDateRangeEnd(2025, 11, 11);
        picker.setTimeRangeStart(calendar.get(Calendar.HOUR_OF_DAY),calendar.get(Calendar.MINUTE)+1 );
//        接收到推送消息 :{"type":1,"template_id":"13"}
        picker.setOnDateTimePickListener(new DateTimePicker.OnYearMonthDayTimePickListener() {

            @Override

            public void onDateTimePicked(String year, String month, String day, String hour, String minute) {
                templatedataRemindTv.setText(String.format("%s-%s-%s %s:%s:%s",year,month,day,hour,minute,"00"));
                String remind_time = templatedataRemindTv.getText().toString();
//                                    Log.e("remind_time = " , ""+ remind_time);
                BaseQuestStart.setTemplateRemind(TeamTemplateDataActivity.this, template_id , remind_time);
                //  showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);

            }

        });

        picker.show();

    }
}
