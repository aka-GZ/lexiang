package com.wq.main

import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.http.NetRequestHandler
import com.sunrun.sunrunframwork.uibase.BaseActivity
import com.wq.common.model.Const.CODE_OK
import com.wq.common.model.GroupListObj
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_GROUP_LIST_CODE
import com.wq.common.quest.BaseQuestConfig.QUEST_SIGNOUT_TEAM_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.SESSION
import com.wq.common.util.isOk
import org.jetbrains.anko.toast

/**
 * 请求代理类，做一些全局数据请求和缓存
 * Created by weiquan on 2017/6/8.
 */

class RequestDelegate (val that: BaseActivity){

    fun doRequest() {
        BaseQuestStart.getGroupList(that, "0", "10")
    }

    fun nofityUpdate(requestCode: Int, baseBean: BaseBean) {
        when (requestCode) {
            QUEST_GET_GROUP_LIST_CODE -> {
                if(baseBean.status==CODE_OK) {
                    val list = baseBean.Data<List<GroupListObj>>();
                    if (list.isNotEmpty()) {//缓存团队信息
                        SESSION("group_name", list[0].group_name)
                        SESSION("group_id", list[0].group_id)
                    }
                }
            }
            QUEST_SIGNOUT_TEAM_CODE->{
                that.toast(baseBean.msg)
                if(baseBean.isOk()){

                }
            }
        }
    }
}
