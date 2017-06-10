package com.wq.template

import android.os.Bundle
import android.text.TextUtils
import butterknife.ButterKnife
import com.sunrun.sunrunframwork.adapter.SelectableAdapter.MULTISELECT
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uibase.BaseActivity
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.view.sidebar.CharacterParser
import com.sunrun.sunrunframwork.view.sidebar.PinyinComparator
import com.sunrun.sunrunframwork.view.sidebar.SideBarUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshBase
import com.wq.base.LBaseActivity
import com.wq.common.model.Const.CODE_OK
import com.wq.common.quest.BaseQuestConfig.QUEST_DEL_TEAM_MEMBER_CODE
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEAM_MEMBER_LIST_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.*
import com.wq.project01.R
import com.wq.template.adapters.SelectPeopleSortAdapter
import com.wq.template.mode.PeopleEntity
import kotlinx.android.synthetic.main.ui_activity_manager_friends.*
import org.jetbrains.anko.toast
import java.util.*

/**
 * 团队成员
 */

class PeopleManagerActivity : BaseActivity() {
    var friends: List<PeopleEntity> = ArrayList()//成员列表集合
    var selectPeopleSortAdapter = SelectPeopleSortAdapter(this, ArrayList())//适配器
    var characterParser = CharacterParser.getInstance()//字符串-拼音 解析器,
    var pinyinComparator: PinyinComparator = PinyinComparator()//拼音排序比较器
    var ids: List<String>? = null                           //已选id
    var names: String? = null
    var group_id:String?=null;
    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.ui_activity_manager_friends)
        ButterKnife.bind(this)
        sidrbar.setTextView(tv_dialog)
        //获取已选ids和名字
//        ids = SESSION("ids")
//        names = SESSION("names")
        refreshLayout.mode = PullToRefreshBase.Mode.DISABLED
        initListener()
        refreshLayout.setAdapter(selectPeopleSortAdapter)
        selectPeopleSortAdapter.selectMode(MULTISELECT)
        group_id=SESSION("group_id");
        getTeamMemberList();
    }

     fun getTeamMemberList(){
         BaseQuestStart.getTeamMemberList(this, group_id, 0)
     }

    private fun initListener() {
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener { s ->
            //该字母首次出现的位置
            val position = selectPeopleSortAdapter.getPositionForSection(s[0].toInt())
            if (position != -1) {
                refreshLayout.refreshableView.setSelection(position)
            }
        }
        titleBar.setRightAction {
            returnSelectedVal();
        }
        //lvSelectFriends.setEmptyView(GetEmptyViewUtils.GetEmptyView(that,R.mipmap.img_nodata,"暂无联系人"));
        refreshLayout.setOnItemClickListener { parent, view, position, id ->
            val realPosition = position - refreshLayout.refreshableView.headerViewsCount
            selectPeopleSortAdapter.select(realPosition)
        }
        edit_search.onTextChanged { charSequence, start, count, after ->
            filterData(charSequence.toString())
        }

        btn_add.setOnClickListener { AddDialogFragment().show(supportFragmentManager, "add") }
        btn_remove.setOnClickListener {
            try {
                var selectData= selectPeopleSortAdapter.allCheckData.list2list { it.uid };
                if(!selectData.isEmpty()){
                    AlertDialogUtil.showConfimDialog(this,"删除操作不可撤销,是否继续",0,{
                        UIUtils.showLoadDialog(this,"删除中")
                        BaseQuestStart.delTeamMember(this,group_id,selectData)
                    },null)

                }else{
                    toast("请选择要删除的成员")
                }
               // verify(edit_search, VerifyerSet.EmptyVerifyer("请输入搜索内容"));
              //  toast("移除")
            } catch (e: Exception) {
               // toast(e.localizedMessage)
            }


        }
    }

    private fun returnSelectedVal() {
        val friendList = friends
                .filter { selectPeopleSortAdapter.isSelected(it) }//过滤选中的数据
        ids = friendList.list2list { it.uid }//转换为字符串集合
        names = friendList.toString { it.user_name }
        SESSION("ids", ids)
        SESSION("names", names)
        setResult(LBaseActivity.RESULT_OK)
        finish()
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_GET_TEAM_MEMBER_LIST_CODE -> {
                bean.Data<List<PeopleEntity>>()?.let {
                    this.friends=it;
                    setData2List(friends=it);
                }
            }
            QUEST_DEL_TEAM_MEMBER_CODE->{
                if(bean.status==CODE_OK){
                    getTeamMemberList()
                }
                toast(bean.msg)
            }
        }
        super.nofityUpdate(requestCode, bean)
    }

    private fun setData2List(friends: List<PeopleEntity>) {
        SideBarUtils.filledData(friends)
        // 根据a-z进行排序源数据
        /**
         * 根据拼音来排列ListView里面的数据类
         */

        Collections.sort(friends, pinyinComparator)
        with(selectPeopleSortAdapter) {
            data=friends
            notifyDataSetChanged()
        }
        ids?.let {
            val splitIds = ids;
            friends.indices
                    .filter { (splitIds!!.contains(friends[it].uid)) && !selectPeopleSortAdapter.isSelected(it) }
                    .forEach { selectPeopleSortAdapter.selectPosition = it }
            selectPeopleSortAdapter.notifyDataSetChanged()
        }

    }

    /**
     * 根据输入框中的值来过滤数据并更新ListView

     * @param filterStr
     */
    private fun filterData(filterStr: String) {
        val filterDateList = ArrayList<PeopleEntity>()
        if (TextUtils.isEmpty(filterStr)) {
            filterDateList.addAll(friends)
        } else {
            filterDateList.clear()
            filterDateList.addAll(friends.filter {
                val name = it.user_name
                name.toLowerCase().indexOf(filterStr.toLowerCase()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr)
            })
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator)
        selectPeopleSortAdapter.updateListView(filterDateList)
        selectPeopleSortAdapter.notifyDataSetChanged()
    }
}
