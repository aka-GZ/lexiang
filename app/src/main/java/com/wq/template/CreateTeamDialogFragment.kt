package com.wq.template


import android.os.Bundle
import android.view.View
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.wq.base.UIBindDialogFragment
import com.wq.common.quest.BaseQuestConfig.QUEST_ADD_TEAM_CODE
import com.wq.common.quest.BaseQuestConfig.QUEST_JOIN_TEAM_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.isOk
import com.wq.project01.R
import kotlinx.android.synthetic.main.dialog_add_people.*
import org.jetbrains.anko.support.v4.toast

/**
 * 创建团队
 * Created by WQ on 2017/6/7.
 */

class CreateTeamDialogFragment : UIBindDialogFragment() {
    var teamCode:String?=null;
    override fun getLayoutId(): Int {
        return R.layout.dialog_addteam_people
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamCode=tv_content.text.toString();
        edit_content.hint="请输入团队名称"
        tv_content.text="创建团队"
        submit.setOnClickListener {
            UIUtils.showLoadDialog(that,"请求中..")
            BaseQuestStart.addTeam(this,edit_content.text.toString())
        }
        cancel.setOnClickListener { dismiss() }
    }

    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when(requestCode){
            QUEST_ADD_TEAM_CODE->{
                if(bean.isOk()){
                    dismiss()
                }
                toast(bean.msg)
            }
        }
        super.nofityUpdate(requestCode, bean)
    }

}
