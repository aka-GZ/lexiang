package com.wq.template

import android.os.Bundle
import butterknife.ButterKnife
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.utils.log.Logger
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshBase
import com.wq.base.LBaseActivity
import com.wq.common.quest.BaseQuestConfig.*
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.isOk
import com.wq.project01.R
import com.wq.template.adapters.RechageOptionAdapter
import com.wq.template.mode.PeopleEntity
import com.wq.vip.mode.RechargeOption
import kotlinx.android.synthetic.main.activity_buy_vip.*
import org.jetbrains.anko.toast

/**
 * 开通企业版
 */

class BuyVipActivity : LBaseActivity() {

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_buy_vip)
        UIUtils.showLoadDialog(that)
        BaseQuestStart.getRechargeOption(this);
        refreshLayout.mode=PullToRefreshBase.Mode.DISABLED
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_GET_RECHARGE_OPTION_CODE -> {
                bean.Data<List<RechargeOption>>()?.let {
                    refreshLayout.setAdapter(RechageOptionAdapter(this,it))
                }
            }
            QUEST_RECHARGE_MEMBER_CODE->{
                if(bean.isOk()){
                    Logger.D(bean.Data())
                }else{
                    toast(bean.msg)
                }
            }
        }
        super.nofityUpdate(requestCode, bean)
    }


}
