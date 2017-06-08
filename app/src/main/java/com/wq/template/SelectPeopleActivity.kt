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
import com.wq.common.util.onTextChanged
import com.wq.common.util.toString
import com.wq.project01.R
import com.wq.template.adapters.SelectPeopleSortAdapter
import com.wq.template.mode.PeopleEntity
import kotlinx.android.synthetic.main.ui_activity_select_friends.*
import java.util.*

/**
 */

class SelectPeopleActivity : BaseActivity() {
    var friends: ArrayList<PeopleEntity> = ArrayList()
    var selectPeopleSortAdapter = SelectPeopleSortAdapter(this, friends)
    var characterParser = CharacterParser.getInstance()
    var pinyinComparator: PinyinComparator = PinyinComparator()
    var ids: String? = null
    var names: String? = null
    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.ui_activity_select_friends)
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
        friends = ArrayList<PeopleEntity>()
        friends.add(PeopleEntity("A姓名1"))
        friends.add(PeopleEntity("B姓名1"))
        friends.add(PeopleEntity("C姓名1"))
        friends.add(PeopleEntity("D姓名1"))
        friends.add(PeopleEntity("E姓名1"))
        friends.add(PeopleEntity("姓名1"))
        setData2List(friends)
        //        BaseQuestStart.getFriendList(this, 1);
    }

    private fun returnSelectedVal() {
        val friendList = friends
        val tmpList = friendList.filter { selectPeopleSortAdapter.isSelected(it) }
        ids = tmpList.toString { it.fuid }
        names = tmpList.toString { it.name }
        intent.putExtra("ids", ids)
        intent.putExtra("names", names)
        setResult(RESULT_OK, intent)
        finish()
    }


    override fun nofityUpdate(requestCode: Int, bean: BaseBean?) {
        //        switch (requestCode) {
        //            case QUEST_BABY_FRIEND_LIST_CODE:
        //                if (bean.status == 1) {
        //                    friends = bean.Data();
        //                  setData2List(friends);
        //
        //                }
        //                break;
        //        }
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
                    .filter { (friends[it].fuid in splitIds) && !selectPeopleSortAdapter.isSelected(it) }
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