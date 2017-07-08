package com.wq.template.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.http.utils.DateUtil
import com.sunrun.sunrunframwork.uibase.BaseActivity
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView
import com.wq.base.LBaseActivity
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.GroupListObj
import com.wq.common.model.MyTemplateBean
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.AlertDialogUtil
import com.wq.project01.R

/**
 * 我的团队适配器
 * Created by weiquan on 2017/6/7.
 */
class MyGroupAdapter(var context: BaseActivity, data: List<GroupListObj>, layoutId: Int = R.layout.item_my_group) :
        ViewHolderAdapter<GroupListObj>(context, data, layoutId) {
    override fun fillView(holder: ViewHodler, mItem: GroupListObj, position: Int) {
        with(mItem) {
            holder.setText(R.id.tv_title, group_name)
            holder.setText(R.id.tv_descript, "团队码：$group_number")
            holder.setClickListener(R.id.btn_delete){
                //删除
//                if (!context.getSession().hasValue("group_id")) {
//                    UIUtils.shortM("尚未加入团队")
//                    return
//                }
                AlertDialogUtil.showConfimDialog(mContext, null, 0, View.OnClickListener {
                    UIUtils.showLoadDialog(mContext, "操作中..")
                    BaseQuestStart.SignOutTeam(context, null,group_id)
                }, null)
            }

        }

    }

}
