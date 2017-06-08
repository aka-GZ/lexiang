package com.wq.template.adapters

import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ListAdapter
import android.widget.ListView
import com.sunrun.sunrunframwork.adapter.ViewHodler
import com.sunrun.sunrunframwork.adapter.ViewHolderAdapter
import com.sunrun.sunrunframwork.http.utils.DateUtil
import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView
import com.wq.common.boxing.GlideMediaLoader
import com.wq.common.model.MyTemplateBean
import com.wq.project01.R

/**
 * 我的模板适配器
 * Created by weiquan on 2017/6/7.
 */
class MyTemplateAdapter(context: Context, data: List<MyTemplateBean>, layoutId: Int = R.layout.item_my_template) :
        ViewHolderAdapter<MyTemplateBean>(context, data, layoutId) {
    override fun fillView(holder: ViewHodler, mItem: MyTemplateBean, position: Int) {
        when {
            mItem.template_id != null  ->
                with(mItem) {
                    GlideMediaLoader.load(mContext, holder.getView<View>(R.id.img_icon), template_cover_img_url)
                    holder.setText(R.id.tv_titlle, template_name)
                    holder.setText(R.id.tv_time, DateUtil.getTimeText(template_add_time))
                    holder.setText(R.id.tv_zhuanf_number, "已转发${forwardingtimes}次")
                    holder.setText(R.id.tv_descript, "暂无描述$forwardingtimes")
                }

        }

    }


}
fun <T> ListView.Adapter(data:List<T>?,layout:Int,fillView:( ViewHodler,  T,  Int)->Unit){
    adapter=object:ViewHolderAdapter<T>(this.context,data,layout) {
        override fun fillView(holder: ViewHodler, mItem: T, position: Int) {
            fillView.invoke(holder,mItem,position);
        }
    }
}