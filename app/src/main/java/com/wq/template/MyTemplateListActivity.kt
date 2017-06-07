package com.wq.template

import android.os.Bundle
import android.view.View
import android.widget.BaseAdapter
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.http.utils.DateUtil
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.wq.base.LPageActivity
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.MyTemplateBean
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEMPLATE_LIST_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.project01.R
import com.wq.template.adapters.MyTemplateAdapter
import kotlinx.android.synthetic.main.activity_team_template_list.*

/**
 * 我的模板
 * Created by weiquan on 2017/6/2.0
 */

class MyTemplateListActivity : LPageActivity<MyTemplateBean>() {

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_team_template_list)
        setPullListener(refresh_layout)
        refresh_layout.setOnItemClickListener { adapterView, view, i, l ->
            val dialogView = View.inflate(that, R.layout.dialog_send_info, null)
            val dialog = UIUtils.createDialog(that, dialogView)
        }
    }

    private var page = 1
    override fun loadData(page: Int) {
        //firstRow 开始记录数   , listRows 每页显示数量
        BaseQuestStart.getTemplateList(this, page, null,null);
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_GET_TEMPLATE_LIST_CODE ->{
                //log 设置 tag为NetServer 可以查看请求情况
                    val list = bean.Data<List<MyTemplateBean>>()//获取数据内容
                    setDataToView(list,refresh_layout.refreshableView)
            }
        }
    }

    override fun getAdapter(list: List<MyTemplateBean>): BaseAdapter {
        return MyTemplateAdapter(this,list)
    }
}
