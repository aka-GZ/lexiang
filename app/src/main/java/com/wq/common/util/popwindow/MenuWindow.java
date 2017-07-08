package com.wq.common.util.popwindow;

import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.sunrun.sunrunframwork.uiutils.UIUtils;
import com.wq.base.LBaseActivity;
import com.wq.common.quest.BaseQuestStart;
import com.wq.common.util.AlertDialogUtil;
import com.wq.common.util.IntentUtil;
import com.wq.common.widget.FixedPopupWindow;
import com.wq.project01.R;
import com.wq.template.AddTeamDialogFragment;
import com.wq.template.AddTemplateActivity;
import com.wq.template.CreateTeamDialogFragment;
import com.wq.template.MyTeamListActivity;
import com.wq.template.PeopleManagerActivity;

/**
 * 个人右上角,操作框
 * Created by weiquan on 2017/6/6.
 */

public class MenuWindow {
    /**
     * 首页操作框
     *
     * @param context
     * @param anchor
     */
    public static void showHomePopDialog(final LBaseActivity context, View anchor) {
        final PopupWindow popupWindow = new FixedPopupWindow(-1, -1);
        popupWindow.setContentView(View.inflate(context, R.layout.pop_more_handle_view, null));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.popAnim);
        popupWindow.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.black_lucency_88)));
        // 需要设置一下此参数，点击外边可消失
        // 设置点击窗口外边窗口消失
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
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
                //创建模板
                IntentUtil.startActivity(context, AddTemplateActivity.class);
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //团队成员
//                IntentUtil.startActivity(context, PeopleManagerActivity.class);
                IntentUtil.startActivity(context, MyTeamListActivity.class);
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //加入团队
                new AddTeamDialogFragment().show(context.getSupportFragmentManager(),"add_team");

                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建团队
                new CreateTeamDialogFragment().show(context.getSupportFragmentManager(),"create_team");
                popupWindow.dismiss();
            }
        });
        layoutView.findViewById(R.id.item_5).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //删除团队
                popupWindow.dismiss();
                if(!context.getSession().hasValue("group_id")){
                    UIUtils.shortM("尚未加入团队");
                    return;
                }
                AlertDialogUtil.showConfimDialog(context, null, 0, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        UIUtils.showLoadDialog(context,"操作中..");
                        BaseQuestStart.SignOutTeam(context,null,context.getSession().getString("group_id"));
                    }
                },null);

            }
        });
//        popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY,0,0);
        popupWindow.showAsDropDown(anchor, 0, 0);
    }
}
