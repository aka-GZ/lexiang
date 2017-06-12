package com.wq.template


import android.os.Bundle
import android.view.View
import com.sunrun.sunrunframwork.bean.BaseBean
import com.wq.base.UIBindDialogFragment
import com.wq.common.model.TeamTemplateListObj
import com.wq.common.quest.BaseQuestConfig.QUEST_ADD_TEAM_CODE
import com.wq.common.util.SESSION
import com.wq.common.util.isOk
import com.wq.common.util.toString
import com.wq.project01.R
import kotlinx.android.synthetic.main.dialog_send_info.*
import org.jetbrains.anko.support.v4.toast

/**
 * 团队转发情况
 * Created by WQ on 2017/6/7.
 */

class TeamZhuanDialogFragment : UIBindDialogFragment() {
    var teamItem: TeamTemplateListObj?=null;
    override fun getLayoutId(): Int {
        return R.layout.dialog_send_info
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        teamItem=SESSION("team_template")
        teamItem?.let {
            edit_1.setText(it.forwarded.toString { it.user_name })
            edit_2.setText(it.not_forward.toString { it.user_name })
        }
        btn_back.setOnClickListener {
            dismiss()
        }
        btn_again.setOnClickListener {
            dismiss()
        }


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
