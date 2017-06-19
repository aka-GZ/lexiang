package com.wq.vip.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sunrun.sunrunframwork.http.NetRequestHandler;
import com.sunrun.sunrunframwork.uiutils.DisplayUtil;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.sunrun.sunrunframwork.utils.ScreenUtils;
import com.wq.common.boxing.GlideMediaLoader;
import com.wq.common.model.TemplateDataObj;
import com.wq.common.quest.BaseQuestStart;
import com.wq.project01.R;
import com.wq.vip.mode.RechargeOption;

import java.util.List;

/**
 * Created by Zheng on 2017/6/11.
 */

public class RechageOptionAdapter_2 extends BaseAdapter {

    Context ctx;
    List<RechargeOption> list;
    int imgW;
    public RechageOptionAdapter_2(Context ctx , List<RechargeOption> list) {
        this.ctx = ctx;
        this.list = list;
//        imgW= ScreenUtils.WHD(ctx)[0];
//        imgW=(imgW- DisplayUtil.dp2px(ctx,46))/3;

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
            view = LayoutInflater.from(ctx).inflate(R.layout.item_recharge_option, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_title= (TextView) view.findViewById(R.id.tv_title);
            viewHolder.tv_price= (TextView) view.findViewById(R.id.tv_price);
            viewHolder.tv_day= (TextView) view.findViewById(R.id.tv_day);
            viewHolder.btnBuy= (Button) view.findViewById(R.id.btnBuy);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        recharge = list.get(position);
        viewHolder.tv_title.setText(recharge.getOption_name());
        viewHolder.tv_price.setText("¥" + recharge.getPrice()+ "/" + recharge.getDay() + "天");
        viewHolder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIUtils.showLoadDialog(ctx,"请求中...");
                Log.e("=======",recharge.getOption_id()+"");
                BaseQuestStart.rechargeMember((NetRequestHandler) ctx,recharge.getOption_id());
            }
        });


        return view;

    }
    RechargeOption recharge;

    public class ViewHolder{

        TextView tv_title,tv_price,tv_day;
        Button btnBuy;

    }

}
