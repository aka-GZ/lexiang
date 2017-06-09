package com.wq.template

import android.os.Bundle
import android.text.TextUtils
import butterknife.ButterKnife
import com.sunrun.sunrunframwork.adapter.SelectableAdapter.MULTISELECT
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uibase.BaseActivity
import com.sunrun.sunrunframwork.view.sidebar.CharacterParser
import com.sunrun.sunrunframwork.view.sidebar.PinyinComparator
import com.sunrun.sunrunframwork.view.sidebar.SideBarUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshBase
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEAM_MEMBER_LIST_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.SESSION
import com.wq.common.util.list2list
import com.wq.common.util.onTextChanged
import com.wq.common.util.toString
import com.wq.project01.R
import com.wq.template.adapters.SelectPeopleSortAdapter
import com.wq.template.mode.PeopleEntity
import kotlinx.android.synthetic.main.ui_activity_select_friends.*
import java.util.*

/**
 * 团队成员.选择提醒谁看
 */

class SelectPeopleActivity : BaseActivity() {
    var friends: ArrayList<PeopleEntity> = ArrayList()//成员列表集合
    var selectPeopleSortAdapter = SelectPeopleSortAdapter(this, friends)//适配器
    var characterParser = CharacterParser.getInstance()//字符串-拼音 解析器,
    var pinyinComparator: PinyinComparator = PinyinComparator()//拼音排序比较器
    var ids: List<String>? = null                           //已选id
    var names: String? = null

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.ui_activity_select_friends)
        ButterKnife.bind(this)
        sidrbar.setTextView(tv_dialog)
        //获取已选ids和名字
        ids = SESSION("ids")
        names = SESSION("names")
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
        findViewById(R.id.test).setOnClickListener { AddDialogFragment().show(supportFragmentManager, "add") }
        refreshLayout.mode = PullToRefreshBase.Mode.DISABLED
        //lvSelectFriends.setEmptyView(GetEmptyViewUtils.GetEmptyView(that,R.mipmap.img_nodata,"暂无联系人"));
        refreshLayout.setOnItemClickListener { parent, view, position, id ->
            val realPosition = position - refreshLayout.refreshableView.headerViewsCount
            selectPeopleSortAdapter.select(realPosition)
        }
        edit_search.onTextChanged { charSequence, start, count, after ->
            filterData(charSequence.toString())
        }
        refreshLayout.setAdapter(selectPeopleSortAdapter)
        selectPeopleSortAdapter.selectMode(MULTISELECT)
        BaseQuestStart.getTeamMemberList(this, SESSION("group_id"), 0)
    }

    private fun returnSelectedVal() {
        val friendList = friends
                .filter { selectPeopleSortAdapter.isSelected(it) }//过滤选中的数据
        ids = friendList.list2list(friends) { it.uid }//转换为字符串集合
        names = friendList.toString { it.name }
        SESSION("ids", ids)
        SESSION("names", names)
        setResult(RESULT_OK)
        finish()
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_GET_TEAM_MEMBER_LIST_CODE -> {
                bean.Data<List<PeopleEntity>>()?.let {
                    setData2List(it);
                }
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
            data.clear()
            data.addAll(friends)
            notifyDataSetChanged()
        }
        ids?.let {
            val splitIds = ids;
            friends.indices
                    .filter { (friends[it] == splitIds) && !selectPeopleSortAdapter.isSelected(it) }
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
                val name = it.getName()
                name.toLowerCase().indexOf(filterStr.toLowerCase()) != -1
                        || characterParser.getSelling(name).startsWith(filterStr)
            })
        }

        // 根据a-z进行排序
        Collections.sort(filterDateList, pinyinComparator)
        selectPeopleSortAdapter.updateListView(filterDateList)
        selectPeopleSortAdapter.notifyDataSetChanged()
        //        if(filterDateList.size()<=0){
        //            mClearEditText.startAnimation(ClearEditText.shakeAnimation(5));
        //        }
    }
}
