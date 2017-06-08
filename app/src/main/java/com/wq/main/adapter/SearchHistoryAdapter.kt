package com.wq.main.adapter

import android.content.Context
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.wq.common.model.ClassTemplateBean
import com.wq.common.model.HomeTemplateBean
import com.wq.project01.R
import java.text.FieldPosition

/**
 * Created by weiquan on 2017/6/6.
 */
class SearchHistoryAdapter(context: Context, data: List<ClassTemplateBean>?, layout :Int= R.layout.item_tag_text) :
        ViewHolderAdapter<ClassTemplateBean>(context, data,layout) {
    override fun fillView(holder: ViewHodler?, mItem: ClassTemplateBean?, position: Int) {
        holder?.setText(R.id.tv_titlle,mItem?.class_name)
    }

}