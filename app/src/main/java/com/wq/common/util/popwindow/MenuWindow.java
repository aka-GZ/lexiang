package com.wq.common.util.popwindow;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.widget.PopupWindow;

import com.wq.common.util.IntentUtil;
import com.wq.project01.R;
import com.wq.template.AddTemplateActivity;
import com.wq.template.PeopleManagerActivity;
import com.wq.template.SelectPeopleActivity;

/**
 * Created by weiquan on 2017/6/6.
 */

public class MenuWindow {
    /**
     * 首页操作框
     *
     * @param context
     * @param anchor
     */
    public static void showHomePopDialog(final Activity context, View anchor) {
        final PopupWindow popupWindow = new PopupWindow(-1, -1);
        popupWindow.setContentView(View.inflate(context, R.layout.pop_more_handle_view, null));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popAnim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.black_lucency_88)));
        View layoutView = popupWindow.getContentView();
        layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StartIntent.startFMInviteAvtivity(context);
                IntentUtil.startActivity(context, AddTemplateActivity.class);
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentUtil.startActivity(context, SelectPeopleActivity.class);
//                StartIntent.startRidesInfoActivity(context);
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                StartIntent.startHelpAndBackActivity(context);
                IntentUtil.startActivity(context,PeopleManagerActivity.class);
                popupWindow.dismiss();
            }
        });
//        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,0,0);
        popupWindow.showAsDropDown(anchor, 0, 0);
    }
}
