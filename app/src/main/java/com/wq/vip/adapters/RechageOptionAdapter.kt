package com.wq.template.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.http.utils.DateUtil
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.MyTemplateBean
import com.wq.common.quest.BaseQuestStart
import com.wq.project01.R
import com.wq.template.BuyVipActivity
import com.wq.vip.BuyVipActivity_2
import com.wq.vip.mode.RechargeOption

/**
 * 充值选项适配器
 * Created by weiquan on 2017/6/7.
 */
class RechageOptionAdapter(var context: BuyVipActivity, data: List<RechargeOption>, layoutId: Int = R.layout.item_recharge_option) :
        ViewHolderAdapter<RechargeOption>(context, data, layoutId) {
    override fun fillView(holder: ViewHodler, mItem: RechargeOption, position: Int) {
                with(mItem) {
                    holder.setText(R.id.tv_titlle, option_name)
                    holder.setText(R.id.tv_price, "¥${price}/${day}天")
                    holder.setClickListener(R.id.btnBuy,{
                        UIUtils.showLoadDialog(context,"请求中...")
                        BaseQuestStart.rechargeMember(context,option_id)
                    })
                }

    }


}
