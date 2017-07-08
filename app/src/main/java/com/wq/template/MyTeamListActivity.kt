package com.wq.template

import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.BaseAdapter
import com.sunrun.sunrunframwork.bean.BaseBean
import com.wq.base.LPageActivity
import com.wq.common.model.GroupListObj
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_GROUP_LIST_CODE
import com.wq.common.quest.BaseQuestConfig.QUEST_SIGNOUT_TEAM_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.*
import com.wq.project01.R
import com.wq.template.adapters.MyGroupAdapter
import kotlinx.android.synthetic.main.activity_team_template_list.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import kotlin.properties.Delegates

/**
 * 我的团队
 * Created by weiquan on 2017/6/2.0
 */

class MyTeamListActivity : LPageActivity<GroupListObj>() {
    var isSelectMode by Delegates.notNull<Boolean>()//是否是选团队成员
    var isTeamTemplate by Delegates.notNull<Boolean>()//是否是查看团队模板
    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_team_template_list)
        setPullListener(refresh_layout)
        titleBar.setTitle("团队列表")
        isSelectMode=intent.getBooleanExtra("isSelectMode",false)
        isTeamTemplate=intent.getBooleanExtra("isTeamTemplate",false);
        GetEmptyViewUtils.bindEmptyView(refresh_layout,0,"尚未加入团队",true)
    }

    private var page2 = 1
    override fun loadData(page: Int) {
//        var str="哈哈"+page+"balala";
        var str="哈哈哈$page2哈哈哈"
        //firstRow 开始记录数   , listRows 每页显示数量
        BaseQuestStart.getGroupList(this, "$page","20");
//        setDataToView(arrayListOf(MyTemplateBean(),MyTemplateBean()),refresh_layout.refreshableView)
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_GET_GROUP_LIST_CODE -> {
                //log 设置 tag为NetServer 可以查看请求情况
                val list = bean.Data<List<GroupListObj>>()//获取数据内容
                setDataToView(list, refresh_layout.refreshableView)
                refresh_layout.itemClick { index ->
                   // var position=index-refresh_layout.refreshableView.headerViewsCount;
                    SESSION("group_name", list[index].group_name)
                    SESSION("group_id", list[index].group_id)
                    if(isTeamTemplate){
                        startActivity<TeamTemplateListActivity>()
                    }else
                    if(isSelectMode) {
                        startActivityForResult(Intent(that,SelectPeopleActivity::class.java),123)// 去成员选择页面去
                    }else{
                        startActivity<PeopleManagerActivity>()

                    }
                    //IntentUtil.startMyTemplateDataActivity(that, listData[index].template_name, listData[index].template_id)
                }
                refresh_layout.refreshableView.setOnItemLongClickListener { adapterView, view, index, l ->
                    try {
                        val position=index-refresh_layout.refreshableView.headerViewsCount;
                        val clipboard:ClipboardManager= getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager;
                        clipboard.text=list[position].group_number;
                        toast("团队码已复制")
                    }catch (e:Exception){
                        e.printStackTrace()
                    }

                    true
                }
            }
           QUEST_SIGNOUT_TEAM_CODE ->{
                that.toast(bean.msg)
                if(bean.isOk()){
                    reshPage()
                }
            }
        }
    }

    override fun getAdapter(list: List<GroupListObj>): BaseAdapter {
        return MyGroupAdapter(this, list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            123->{
                setResult(resultCode,data)
                finish()
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}
