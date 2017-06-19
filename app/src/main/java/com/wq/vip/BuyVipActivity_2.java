package com.wq.vip;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.sunrun.sunrunframwork.bean.BaseBean;
import com.sunrun.sunrunframwork.pay.alipay.PayResult;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
import com.wq.base.LBaseActivity;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.widget.TitleBar;
import com.wq.project01.R;
import com.wq.vip.adapters.RechageOptionAdapter_2;
import com.wq.vip.mode.RechargeOption;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.sunrun.sunrunframwork.pay.PayConfig.SDK_PAY_FLAG;
import static com.wq.common.model.Const.CODE_NEED_PAY;
import static com.wq.common.model.Const.CODE_OK;
import static com.wq.common.quest.BaseQuestConfig.QUEST_GET_RECHARGE_OPTION_CODE;
import static com.wq.common.quest.BaseQuestConfig.QUEST_RECHARGE_MEMBER_CODE;

/**
 * Created by Zheng on 2017/6/18.
 */

public class BuyVipActivity_2 extends LBaseActivity {


    @BindView(R.id.titleBar)
    TitleBar titleBar;
    @BindView(R.id.tvLab)
    TextView tvLab;
    @BindView(R.id.refreshLayout)
    PullToRefreshListView refreshLayout;
    @BindView(R.id.activity_main)
    LinearLayout activityMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_buy_vip);
        ButterKnife.bind(this);
        super.onCreate(savedInstanceState);

        getdata();
        init();


    }


    private void init() {
//        titlebar.setTitle("素材市场");
    }

    private void getdata() {

        /**
         * 获取充值会员选项
         * API参数传入方式: GET
         * 传入JSON格式: 无
         * 返回JSON格式: {"meta":{"code":200,"message":"success"},"body":[{"option_id":"1","option_name":"20u6210u5458u4f1au5458","day":"365","price":"30","group_num":"500"},{"option_id":"2","option_name":"30u6210u5458u4f1au5458","day":"365","price":"30","group_num":"500"}]}
         * API_URL_本地: http://localhost:81/api.php/Cube/getRechargeOption
         * API_URL_服务器: http://fx.lyfz.net/wxyx/api.php/Cube/getRechargeOption
         * @return code 200->成功
         * @return option_id 选项ID
         * @return option_name 充值选项名称
         * @return day 充值有效天数
         * @return price 充值选项价格
         * @return group_num 允许添加团队人数 -1不限制
         */
        BaseQuestStart.getRechargeOption(this);
    }


    @Override
    public void nofityUpdate(int requestCode, BaseBean bean) {

        switch (requestCode) {
            case QUEST_GET_RECHARGE_OPTION_CODE:
                if (bean.status == CODE_OK) {
                    List<RechargeOption> list =  bean.Data();//获取数据内容
                    refreshLayout.setAdapter(new RechageOptionAdapter_2(this,list));
                } else {
                    UIUtils.shortM(bean.msg);
                }

                break;
            case QUEST_RECHARGE_MEMBER_CODE:
                    if(bean.status == CODE_NEED_PAY){
                        final String data = bean.Data();//获取数据内容
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                // 构造PayTask 对象
                                PayTask alipay = new PayTask(BuyVipActivity_2.this);
                                // 调用支付接口，获取支付结果
                                String result = alipay.pay(data ,true);
                                Message msg = new Message();
                               // msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);

                            }
                        };
                        // 必须异步调用
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }else{
                        UIUtils.shortM(bean.msg);
                    }

                break;

        }
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {

            PayResult payResult = new PayResult((String) msg.obj);
            /**
             * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
             * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
             * docType=1) 建议商户依赖异步通知
             */
            String resultInfo = payResult.getResult();// 同步返回需要验证的信息

            String resultStatus = payResult.getResultStatus();
            // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
            if (TextUtils.equals(resultStatus, "9000")) {


            } else {

            }

        }
    };


}
