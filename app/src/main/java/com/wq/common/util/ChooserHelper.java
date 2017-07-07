package com.wq.common.util;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.aigestudio.wheelpicker.WheelPicker;
import com.aigestudio.wheelpicker.widgets.WheelDatePicker;
import com.sunrun.sunrunframwork.http.cache.NetSession;
import com.sunrun.sunrunframwork.http.utils.DateUtil;
import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.common.App;
import com.wq.common.widget.CustomWheelDatePicker;
import com.wq.project01.R;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.picker.DatePicker;
import cn.qqtheme.framework.picker.TimePicker;

import static com.sunrun.sunrunframwork.utils.EmptyDeal.empty;

/**
 * 性别,血型等,选择器帮助类
 * Created by WQ on 2016/12/8.
 */

public class ChooserHelper {
    Dialog dialog;
    public WheelDatePicker datePicker;
    public WheelPicker valPicker;

    public OnselectValListener getOnselectValListener() {
        return onselectValListener;
    }

    public void setOnselectValListener(OnselectValListener onselectValListener) {
        this.onselectValListener = onselectValListener;
    }

    public OnselectValListener onselectValListener;


    /**
     * 显示日期的选择器
     *
     * @param context
     * @param textView
     * @param listener
     * @return
     */
    public ChooserHelper showDateChooser(Context context, final TextView textView, final WheelDatePicker.OnDateSelectedListener listener) {
        bulidDateDialog(context, textView, listener);
        return this;
    }





    /**
     * 获取选择的日期
     *
     * @return
     */
    public String getChooseDate() {
        if (datePicker != null) {
            return DateUtil.getStringByFormat(datePicker.getCurrentDate(), DateUtil.dateFormatYMDHMS);
        }
        return null;
    }

    public String getChooseDate(String defVal) {
        if (datePicker != null) {
            return DateUtil.getStringByFormat(datePicker.getCurrentDate(), DateUtil.dateFormatYMDHMS);
        }
        return defVal;
    }


    /**
     * 创建日期选择器
     *
     * @param context
     * @param textView
     * @param listener
     * @return
     */
    Dialog bulidDateDialog(Context context, final TextView textView, final WheelDatePicker.OnDateSelectedListener listener) {
        if (dialog == null) {
            final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_date_wheel, null);
            dialog = UIUtils.createDialog(context, dialogView, true);
//            if(true)
//            return dialog;
            final CustomWheelDatePicker wheelPicker = (CustomWheelDatePicker) dialogView.findViewById(R.id.wheel_date);
            Date tmpDate = (Date) textView.getTag();
            if(tmpDate!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmpDate);
                wheelPicker.setYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                wheelPicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
            }
            wheelPicker.setVisibleItemCount(5);
            wheelPicker.setItemTextColor(context.getResources().getColor(R.color.text4));
            wheelPicker.setSelectedItemTextColor(context.getResources().getColor(R.color.text2));
            wheelPicker.setIndicator(true);
            wheelPicker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
                @Override
                public void onDateSelected(WheelDatePicker picker, Date date) {
                }
            });
            // wheelPicker.setData(data);

        } else {
            if (!empty(textView.getTag())) {
                final WheelDatePicker wheelPicker = (WheelDatePicker) dialog.findViewById(R.id.wheel_date);
                Date tmpDate = (Date) textView.getTag();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmpDate);
                wheelPicker.setYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                wheelPicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
        final WheelDatePicker wheelPicker = (WheelDatePicker) dialog.findViewById(R.id.wheel_date);
        dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = wheelPicker;
                Date tmpDate = wheelPicker.getCurrentDate();
                String birthday = DateUtil.getStringByFormat(tmpDate, "yyyy-MM-dd");
                textView.setText(birthday);
                listener.onDateSelected(wheelPicker, tmpDate);
                textView.setTag(tmpDate);
                wheelPicker.setTag(tmpDate);
                dialog.dismiss();
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }


    Dialog bulidDateTimeDialog(Context context, final TextView textView, final WheelDatePicker.OnDateSelectedListener listener) {
        if (dialog == null) {

            final View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_date_time_wheel, null);
            dialog = UIUtils.createDialog(context, dialogView, true);
            final CustomWheelDatePicker wheelPicker = (CustomWheelDatePicker) dialogView.findViewById(R.id.wheel_date);
            Date tmpDate = (Date) textView.getTag();
            if(tmpDate!=null){
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmpDate);
                wheelPicker.setYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                wheelPicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
            }
            wheelPicker.setVisibleItemCount(5);
            wheelPicker.setItemTextColor(context.getResources().getColor(R.color.text4));
            wheelPicker.setSelectedItemTextColor(context.getResources().getColor(R.color.text2));
            wheelPicker.setIndicator(true);
            wheelPicker.setOnDateSelectedListener(new WheelDatePicker.OnDateSelectedListener() {
                @Override
                public void onDateSelected(WheelDatePicker picker, Date date) {
                }
            });
            // wheelPicker.setData(data);

        } else {
            if (!empty(textView.getTag())) {
                final WheelDatePicker wheelPicker = (WheelDatePicker) dialog.findViewById(R.id.wheel_date);
                Date tmpDate = (Date) textView.getTag();
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(tmpDate);
                wheelPicker.setYearAndMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1);
                wheelPicker.setSelectedDay(calendar.get(Calendar.DAY_OF_MONTH));
            }
        }
        final WheelDatePicker wheelPicker = (WheelDatePicker) dialog.findViewById(R.id.wheel_date);
        dialog.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker = wheelPicker;
                Date tmpDate = wheelPicker.getCurrentDate();
                String birthday = DateUtil.getStringByFormat(tmpDate, "yyyy-MM-dd");
                textView.setText(birthday);
                listener.onDateSelected(wheelPicker, tmpDate);
                textView.setTag(tmpDate);
                wheelPicker.setTag(tmpDate);
                dialog.dismiss();
            }
        });
        if (!dialog.isShowing()) {
            dialog.show();
        }
        return dialog;
    }




    NetSession getSession() {
        return NetSession.instance(App.getInstance());
    }

    public interface OnselectValListener {
        void onSelectValListener(String text, int index);
    }
}
