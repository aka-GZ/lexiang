package com.wq.template;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.base.LBaseActivity;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.ShareHelper;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;
import com.wq.template.adapters.TemplateDataAdapter;

import java.io.File;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_SHOP_TEMPLATE_DATA_CODE;

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
                        }
                    });

                } else if (bean.status == 3003) {
                    UIUtils.shortM(bean.msg);
                    finish();

                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;

        }
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