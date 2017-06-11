package com.wq.main

import android.os.Bundle
import android.widget.BaseAdapter
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.wq.base.LPageActivity
import com.wq.common.model.HomeTemplateBean
import com.wq.common.quest.BaseQuestConfig.GET_SHOP_TEMPLATE_LIST_URL
import com.wq.common.quest.BaseQuestConfig.QUEST_GET_SHOP_TEMPLATE_LIST_CODE
import com.wq.common.quest.BaseQuestStart
import com.wq.common.util.GetEmptyViewUtils
import com.wq.common.util.onTextChanged
import com.wq.main.adapter.HotTemplateAdapter
import com.wq.main.adapter.ShopTemplateAdapter
import com.wq.project01.R
import kotlinx.android.synthetic.main.activity_shop_template_list.*

/**
 * 九宫格市场模板列表
 * Created by weiquan on 2017/6/2.0
 */

class TemplateListActivity : LPageActivity<HomeTemplateBean>() {

    var url:String?=GET_SHOP_TEMPLATE_LIST_URL;//默认请求地址

    override fun onCreate(arg0: Bundle?) {
        super.onCreate(arg0)
        setContentView(R.layout.activity_shop_template_list)
        if(intent.getStringExtra("url")!=null){
            url=intent.getStringExtra("url")
        }
        if(intent.getStringExtra("title")!=null){
            titleBar.setTitle(intent.getStringExtra("title"))
        }
        edit_search.setText(intent.getStringExtra("keywords"));
        edit_search.onTextChanged { charSequence, start, end, len ->
            reshPage()
        }
        setPullListener(refresh_layout)
        GetEmptyViewUtils.bindEmptyView(refresh_layout,0,"暂无数据",true);
    }

    override fun loadData(page: Int) {
        var searchText=edit_search.text.toString()

//        UIUtils.showLoadDialog(that)
        BaseQuestStart.getShopTemplateList(this,url,page,searchText,null)
    }

    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when(requestCode){
            QUEST_GET_SHOP_TEMPLATE_LIST_CODE->{
                bean.Data<List<HomeTemplateBean>>()?.let {
                    setDataToView(it,refresh_layout.refreshableView)
                };
            }
        }
        super.nofityUpdate(requestCode, bean)
    }

    override fun getAdapter(list: List<HomeTemplateBean>): BaseAdapter {
        return ShopTemplateAdapter(this, list)
    }
}
