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
        BaseQuestStart.getClassTemplate(this)
        BaseQuestStart.getShopRecommendTemplateList(this,"0",null,null)
//        BaseQuestStart.getShopR
        tvSearch.setOnClickListener { toast("搜索") }
        hot_more.setOnClickListener { toast("更多热门模板") }
        recommend_mre.setOnClickListener { toast("更多推荐模板") }
        var data= arrayListOf("1","2","2","2","2","2","2");
        typeGrid.adapter= object :ViewHolderAdapter<String>(that,data,R.layout.item_tag_text){
            override fun fillView(holder: ViewHodler, mItem: String, position: Int) {

            }
        }
        hotGridView.adapter=object :ViewHolderAdapter<String>(that, data ,R.layout.item_hot_template){
            override fun fillView(holder: ViewHodler, mItem: String, position: Int) {
            }
        }
        hotGridView.setOnItemClickListener { adapterView, view, i, l ->
            val  picture=PictureShow(that)
            picture.setArgment(arrayListOf("https://ss0.bdstatic.com/94oJfD_bAAcT8t7mm9GUKT-xh_/timg?image&quality=100&size=b4000_4000&sec=1496676029&di=9c7bb354561b7c0078daf69090021783&src=http://pic.58pic.com/58pic/13/61/00/61a58PICtPr_1024.jpg"));
            picture.show()
        }



    }

    override fun nofityUpdate(requestCode: Int, bean: BaseBean) {
        when (requestCode) {
            QUEST_LOGIN_CODE -> {
                //log 设置 tag为NetServer 可以查看请求情况
               // tvContent.text = bean.toString()
                if (bean.status == 200) {
                    val info = bean.Data<LoginInfo>()//获取数据内容
                    Config.putLoginInfo(info)
                }
            }
            QUEST_GETSHOPRECOMMENDTEMPLATELIST_CODE ->{
                Logger.D("${bean.data}")
                val data=bean.Data<List<HomeTemplateBean>>();
                recommendGridView.adapter=object :ViewHolderAdapter<HomeTemplateBean>(that, data ,R.layout.item_hot_template){
                    override fun fillView(holder: ViewHodler, mItem: HomeTemplateBean, position: Int) {
                            holder.setText(R.id.tv_titlle,mItem.template_name)
                            GlideMediaLoader.load(that,holder.getView(R.id.img_icon),mItem.template_cover_img_url)
                    }
                }
            }
            QUEST_GET_CLASS_TEMPLATE_CODE->{
                typeGrid.setOnItemClickListener { adapterView, view, i, l ->toast("点击$i")  }
                typeGrid.adapter= object :ViewHolderAdapter<ClassTemplateBean>(that, bean.data as MutableList<ClassTemplateBean>,R.layout.item_tag_text){
                    override fun fillView(holder: ViewHodler, mItem: ClassTemplateBean, position: Int) {
                        holder.setText(R.id.tv_titlle,mItem.class_name)
                    }
                }

            }

        }
        super.nofityUpdate(requestCode, bean)
    }

    override fun getLayoutRes(): Int {
        return R.layout.fragment_home
    }

}
