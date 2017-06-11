package com.wq.template

import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView

import com.aigestudio.wheelpicker.widgets.WheelDatePicker
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView
import com.wq.base.LPageActivity
import com.wq.common.widget.TitleBar
import com.wq.project01.R

import java.util.ArrayList
import java.util.Arrays
import java.util.Date

import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.http.utils.DateUtil
import com.sunrun.sunrunframwork.view.sidebar.SortModel
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.UseHistoryListBean
import com.wq.common.quest.BaseQuestConfig.QUEST_MY_USE_TEMPLATE_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.*
import kotlinx.android.synthetic.main.activity_use_history_list.*
import org.jetbrains.anko.toast

/**
 * 我的使用记录
 * Created by weiquan on 2017/6/2.0
 */

class UseHistoryListActivity : LPageActivity<UseHistoryListBean>() {


    internal var startTimeChooser = ChooserHelper()
    internal var endTimeChooser = ChooserHelper()
    var startTimeStr:String?=null;
    var endTimeStr:String?=null;
    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_use_history_list)
        ButterKnife.bind(this)
        setPullListener(refreshLayout)
       // startTime.text=DateUtil.getCurrentDate(DateUtil.dateFormatYMD)
        //endTime.text=DateUtil.getCurrentDate(DateUtil.dateFormatYMD)

//        onClick([startTime,endTime,callback={
//            view->
//        });
        GetEmptyViewUtils.bindEmptyView(refreshLayout,0,"暂无数据",true)
        startTime.setOnClickListener {
            startTimeChooser.showDateChooser(that, startTime) { picker, date ->
                startTimeStr=startTime.text.toString();
                if(!setDateAndRefresh()){
                    startTimeStr=null
                }

            }
        }
        endTime.setOnClickListener {
            endTimeChooser.showDateChooser(that, endTime) { picker, date ->
                endTimeStr=endTime.text.toString();
                if(!setDateAndRefresh()){
                    endTimeStr=null
                }

            }
        }
    }

    override fun loadData(page: Int) {
        BaseQuestStart.getMyUseTemplate(this,page,startTimeStr,endTimeStr)
//        setDataToView(list, refreshLayout!!.refreshableView)
    }

    fun setDateAndRefresh():Boolean{
        if(DateUtil.getDateByFormat(startTime.text.toString(),DateUtil.dateFormatYMD).time>DateUtil.getDateByFormat(endTime.text.toString(),DateUtil.dateFormatYMD).time){
            return  false;
        }
        reshPage()
        return true
    }
    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when(requestCode){
            QUEST_MY_USE_TEMPLATE_CODE->{
                if(!bean.isOk()){
                    toast(bean.msg)
                }
                bean.Data<List<UseHistoryListBean>>()?.let {
                    it.forEach {
                        //计算下日期和时间,保存起来,用来做分组
                        var date=DateUtil.getStringByFormat(it.use_time,DateUtil.dateFormatYMD)
                        var time=DateUtil.getStringByFormat(it.use_time,DateUtil.dateFormatHMS)
                        it.useDate=date;
                        it.useTime=time;
                    }
                    setDataToView(it, refreshLayout.refreshableView)
                    refreshLayout.setOnItemClickListener { adapterView, view, i, l ->
                        //跳转到详情页面
                        IntentUtil.startTemplateDataActivity(this,it[i].template_name,it[i].template_id)
//            val dialogView = View.inflate(that, R.layout.dialog_send_info, null)
//            val dialog = UIUtils.createDialog(that, dialogView)
                    }
                }
            }
        }
        super.nofityUpdate(requestCode, bean)
    }
    override fun getAdapter(list: List<UseHistoryListBean>): BaseAdapter {
        return object : ViewHolderAdapter<UseHistoryListBean>(that, list, R.layout.item_use_history) {
            override fun fillView(hodler: ViewHodler, mItem: UseHistoryListBean, position: Int) {
                hodler.setText(R.id.tv_date,mItem.useDate)
                hodler.setText(R.id.tv_title,mItem.template_name)
                GlideMediaLoader.load(mContext,hodler.getView(R.id.img_icon),mItem.template_cover_img_url)
                hodler.setText(R.id.tv_descript,mItem.template_content)
                hodler.setText(R.id.tv_use_number,"累计使用: ${mItem.use_num}")
                hodler.setText(R.id.tv_time,mItem.useTime)
                hodler.setVisibility(R.id.lay_lab, getPositionForSection(position)==position)
            }

             fun getPositionForSection(section: Int): Int {
                for (i in 0..this.count - 1) {//查找第一次使用到的索引位置,
                    if (data[section].useDate == data[i].useDate) {
                        return i
                    }
                }
                return -1
            }
        }
    }

}
