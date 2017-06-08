package com.wq.main.adapter

import android.content.Context
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.uiutils.DisplayUtil
import com.sunrun.sunrunframwork.uiutils.UIUtils
import com.sunrun.sunrunframwork.utils.ScreenUtils
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.HomeTemplateBean
import com.wq.project01.R

/**
 * Created by weiquan on 2017/6/6.
 */
class HotTemplateAdapter(context: Context, data: List<HomeTemplateBean>?,layout :Int= R.layout.item_hot_template) : ViewHolderAdapter<HomeTemplateBean>(context, data,layout) {
    var itemW:Int;
    init {
        val WH=ScreenUtils.WHD(mContext);
        itemW=(WH[0]-DisplayUtil.dip2px(mContext,40f))/3;
    }
    override fun fillView(holder: ViewHodler, mItem: HomeTemplateBean, position: Int) {
        holder.setText(R.id.tv_titlle,mItem.template_name)
        GlideMediaLoader.load(mContext,holder.getView(R.id.img_icon),mItem.template_cover_img_url)
        UIUtils.setViewWH(holder.getView(R.id.img_icon),itemW,itemW);

    }

}