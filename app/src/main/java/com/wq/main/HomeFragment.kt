package com.wq.main

import android.os.Bundle
import android.view.View
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.bean.BaseBean
import com.sunrun.sunrunframwork.uiutils.PictureShow
import com.sunrun.sunrunframwork.utils.log.Logger
import com.wq.base.LBaseFragment
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.ClassTemplateBean
import com.wq.common.model.HomeTemplateBean
import com.wq.common.model.LoginInfo
import com.wq.common.quest.BaseQuestConfig.*
import com.wq.common.quest.BaseQuestStart
import com.wq.common.quest.Config
import com.wq.common.util.IntentUtil
import com.wq.main.adapter.HotTemplateAdapter
import com.wq.main.adapter.SearchHistoryAdapter
import com.wq.project01.R
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.toast

/**
 * 首页
 * Created by WQ on 2017/6/1.
 */

class HomeFragment : LBaseFragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //请求页面数据

        BaseQuestStart.getClassTemplate(this)
        BaseQuestStart.getShopRecommendTemplateList(this, "0", null, null)
        BaseQuestStart.getShopHotTemplateList(this, "0", null, null)
        tvSearch.setOnClickListener {
            IntentUtil.startTemplateListActivity(that, null, null, null)
//            toast("搜索")
        }
        hot_more.setOnClickListener {
            IntentUtil.startTemplateListActivity(that, GET_SHOP_HOT_TEMPLATE_LIST_URL, null, "热门模板")
//            toast("更多热门模板")
        }
        recommend_mre.setOnClickListener {
            IntentUtil.startTemplateListActivity(that, GET_GETSHOPRECOMMENDTEMPLATELIST_URL, null, "推荐模板")
//            toast("更多推荐模板")
        }
    }

    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
        //推荐模板
            QUEST_GETSHOPRECOMMENDTEMPLATELIST_CODE -> {
                Logger.D("${bean.data}")
                val data = bean.Data<List<HomeTemplateBean>>();
                recommendGridView.adapter = HotTemplateAdapter(that, data);
                recommendGridView.setOnItemClickListener { adapterView, view, index, l ->
                    IntentUtil.startTemplateDataActivity(that, data[index].template_name, data[index].template_id)
                }
            }
        //顶部分类.搜索历史
            QUEST_GET_CLASS_TEMPLATE_CODE -> {
                val info = bean.Data<List<ClassTemplateBean>>()//获取数据内容
                typeGrid.adapter = SearchHistoryAdapter(that, info, R.layout.item_tag_text);
                typeGrid.setOnItemClickListener { adapterView, view, i, l ->
                    IntentUtil.startTemplateListActivity(that, null,info[i].class_name,null)
                }
            }
        //热门模板
            QUEST_SHOP_HOT_TEMPLATE_LIST_CODE -> {
                val data = bean.Data<List<HomeTemplateBean>>();
                hotGridView.adapter = HotTemplateAdapter(that, data)
                hotGridView.setOnItemClickListener { adapterView, view, index, l ->
                    IntentUtil.startTemplateDataActivity(that, data[index].template_name, data[index].template_id)
                }
            }

        }
        super.nofityUpdate(requestCode, bean)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

}
