package com.wq.template;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uiutils.PictureShow;
import com.sunrun.sunrunframwork.uiutils.ToastUtils;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.base.LBaseActivity;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.model.TemplateStatisticsObj;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.AlertDialogUtil;
import com.wq.common.util.ChooserHelper;
import com.wq.common.util.ShareHelper;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;
import com.wq.template.adapters.TemplateDataAdapter;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.qqtheme.framework.picker.DateTimePicker;

import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_SHOP_TEMPLATE_DATA_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEMPLATE_STATISTICS_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_SET_TEMPLATE_REMIND_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_TEMPLATE_FORWARD_RECORD_CODE;

/**
 * Created by Zheng on 2017/6/11.
 */

public class ShopTemplateDataActivity extends LBaseActivity {

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
         * 获取市场模板详情数据
         * API参数传入方式: POST
         * 传入JSON格式: {"template_id":"13"}
         * 返回JSON格式: {"meta":{"code":200,"message":"success"}}
         * API_URL_本地: http://localhost:81/api.php/Cube/getShopTemplateData
         * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getShopTemplateData
         * @param template_id->模板ID
         * @return code 200->成功 3001更新数据失败 3003->非会员无法使用会员模板
         * @return template_name->九宫格模板名称标题
         * @return template_content->模板内容
         * @return template_cover_img_url->模板封面图片路径
         * @return is_open->模板是否公开 -1不公开 1公开
         * @return is_member_free->是否会员是会员模板 -1不是 1是
         * @return template_id->模板ID
         * @return template_add_time->模板添加时间
         * @return imgList->九宫格模板图片路径list
         * @return forwarding_times->模板转发次数
         * @return remindPeopleList->九宫格提醒人uid list
         */
        BaseQuestStart.getShopTemplateData(this, template_id);
    }

    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {

        switch (requestCode) {
            case QUEST_GET_SHOP_TEMPLATE_DATA_CODE:
                if (bean.status == CODE_OK) {

                    final TemplateDataObj obj = bean.Data();//获取数据内容

                    titlebar.setTitle("素材市场-" + obj.getTemplate_name());
                    templatedataTv.setText("" + obj.getTemplate_content());
                    TemplateDataAdapter adapter = new TemplateDataAdapter(ShopTemplateDataActivity.this, obj.getImgList());
                    templatedataGv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    shareHelper.saveShareImage(obj, false);
                    templatedataSharedTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            shareHelper.saveShareImage(obj, true);

                            BaseQuestStart.templateForwardRecord(ShopTemplateDataActivity.this, template_id);
                        }
                    });

                    templatedataGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            PictureShow pictureShow=new PictureShow(that);
                            pictureShow.setArgment(obj.getImgList(),i);
                            pictureShow.show();
                        }
                    });
                    templatedataRemindTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onYearMonthDayTimePicker();

                        }
                    });
                    templatedataInformTv.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            BaseQuestStart.getTemplateStatistics(ShopTemplateDataActivity.this, template_id);

                        }
                    });


                } else if (bean.status == 3003) {
                    UIUtils.shortM(bean.msg);
                    finish();

                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;

            case QUEST_TEMPLATE_FORWARD_RECORD_CODE:
                if (bean.status == CODE_OK) {
                    UIUtils.shortM(bean.msg);
                    //  finish();

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
                    new AlertDialog.Builder(ShopTemplateDataActivity.this)
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
                                    BaseQuestStart.setTemplateRemind(ShopTemplateDataActivity.this, template_id , remind_time);
              //  showToast(year + "-" + month + "-" + day + " " + hour + ":" + minute);

            }

        });

        picker.show();

    }

    private String getlmgPathFromCache(String url) {
        FutureTarget<File> future = Glide.with(this)
                .load(url)
                .downloadOnly(100, 100);
        try {
            File cacheFile = future.get();
            String path = cacheFile.getAbsolutePath();
            return path;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}