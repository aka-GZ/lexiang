package com.wq.template

import android.os.Bundle
import android.text.TextUtils
import butterknife.ButterKnife
import com.sunrun.sunrunframwork.adapter.SelectableAdapter.MULTISELECT
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uibase.BaseActivity
import com.sunrun.sunrunframwork.utils.formVerify.VerifyUtil
import com.sunrun.sunrunframwork.utils.formVerify.VerifyUtil.verify
import com.sunrun.sunrunframwork.utils.formVerify.VerifyerSet
import com.sunrun.sunrunframwork.view.sidebar.CharacterParser
import com.sunrun.sunrunframwork.view.sidebar.PinyinComparator
import com.sunrun.sunrunframwork.view.sidebar.SideBarUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshBase
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_TEAM_MEMBER_LIST_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.SESSION
import com.wq.common.util.onTextChanged
import com.wq.common.util.toString
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
    var friends: ArrayList<PeopleEntity> = ArrayList()
    var selectPeopleSortAdapter = SelectPeopleSortAdapter(this, friends)
    var characterParser = CharacterParser.getInstance()
    var pinyinComparator: PinyinComparator = PinyinComparator()
    var ids: String? = null
    var names: String? = null

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.ui_activity_manager_friends)
        ButterKnife.bind(this)
        sidrbar.setTextView(tv_dialog)
        ids = intent.getStringExtra("ids")
        names = intent.getStringExtra("names")
        //设置右侧触摸监听
        sidrbar.setOnTouchingLetterChangedListener { s ->
            //该字母首次出现的位置
            val position = selectPeopleSortAdapter.getPositionForSection(s[0].toInt())
            if (position != -1) {
                refreshLayout.refreshableView.setSelection(position)
            }
        }
        titleBar.setRightAction {
            //                returnSelectedVal();
        }
        btn_add.setOnClickListener { AddDialogFragment().show(supportFragmentManager, "add") }
        btn_remove.setOnClickListener {
            try {
                verify(edit_search, VerifyerSet.EmptyVerifyer("请输入搜索内容"));
                toast("移除")
            } catch (e: Exception) {
                toast(e.localizedMessage)
            }


        }
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
        val tmpList = friendList.filter { selectPeopleSortAdapter.isSelected(it) }
        ids = tmpList.toString { it.uid }
        names = tmpList.toString { it.name }
        intent.putExtra("ids", ids)
        intent.putExtra("names", names)
        setResult(RESULT_OK, intent)
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
            val splitIds = it.split(',').toTypedArray()
            friends.indices
                    .filter { (friends[it].uid in splitIds) && !selectPeopleSortAdapter.isSelected(it) }
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
