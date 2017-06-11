package com.wq.template.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.model.TemplateDataObj;
import com.wq.project01.R;

import java.util.List;

/**
 * Created by Zheng on 2017/6/11.
 */

public class TemplateDataAdapter extends BaseAdapter {

    Context ctx;
    List<TemplateDataObj.ImgListBean> list;

    public TemplateDataAdapter(Context ctx , List<TemplateDataObj.ImgListBean> list) {
        this.ctx = ctx;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(ctx).inflate(R.layout.item_template_data, null);
            viewHolder = new ViewHolder();
            viewHolder.item_template_data_iv= (ImageView) view.findViewById(R.id.item_template_data_iv);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        GlideMediaLoader.load(ctx , viewHolder.item_template_data_iv ,list.get(position).getImg_url());



        return view;

    }


    public class ViewHolder{

        ImageView item_template_data_iv;

    }

}
