package com.wq.template.adapters

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.TextView

import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.view.sidebar.BaseSortAdapter
import com.wq.project01.R
import com.wq.template.mode.PeopleEntity
import com.wq.project01.R.mipmap.icon_select_selected
import com.wq.project01.R.mipmap.icon_select_unselected

/**
 * @param mAct  上下文
 * *
 * @param datas 数据集
 */
class SelectPeopleSortAdapter
(mAct: Activity, datas: List<PeopleEntity>) : BaseSortAdapter<PeopleEntity>(mAct, datas, R.layout.item_select_friends) {

    var isEditMode=true;
    override fun fillView(holder: ViewHodler, sortModel: PeopleEntity, position: Int) {
        val tvName = holder.getView<TextView>(R.id.tv_name)
        val tvLetter = holder.getView<TextView>(R.id.catalog)
        val imageView = holder.getView<ImageView>(R.id.iv_img)
        tvName.text = sortModel.user_name
        holder.setImageResourse(R.id.iv_select, if (isSelected(position)) icon_select_selected else icon_select_unselected)
        //        NetImageUtil.LodeHeadImageView(sortModel.getIcon(),imageView);
        //根据position获取分类的首字母的Char ascii值
        val section = getSectionForPosition(position)
        holder.setVisibility(R.id.iv_select,isEditMode);
        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            tvLetter.visibility = View.VISIBLE
            tvLetter.text = sortModel.getSortLetters()
        } else {
            tvLetter.visibility = View.GONE
        }

        super.fillView(holder, sortModel, position)
    }

}