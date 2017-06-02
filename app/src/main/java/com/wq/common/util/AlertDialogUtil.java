//package com.wq.common.util;
//
//import android.app.Dialog;
//import android.content.Context;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import cn.cnsunrun.shangshengxinghuo.R;
//
//
///**
// * @version V1.0
// * @功能描述: Dialog对话框总工具类
// */
//public final class AlertDialogUtil {
//    /**
//     * 单例模式
//     */
//    private static AlertDialogUtil AlertDialogUtil;
//
//    /**
//     * 显示正在加载的对话框
//     */
//    private Dialog LoadDialog;
//
//    private AlertDialogUtil() {
//    }
//
//    public static AlertDialogUtil getInstences() {
//        if (AlertDialogUtil == null) {
//            AlertDialogUtil = new AlertDialogUtil();
//        }
//        return AlertDialogUtil;
//    }
//
//
//
//    /**
//     * 确认对话框
//     * @param context
//     * @param content
//     * @return
//     */
//    public static Dialog showConfimDialog(final Context context, CharSequence content, int iconId, final View.OnClickListener onConfimListener,final View.OnClickListener onCancelListener) {
//        final Dialog dialog = new Dialog(context, R.style.NoTitleDialog);
//        View dialogView = View.inflate(context, R.layout.dialog_delete_confim, null);
//        dialog.setContentView(dialogView);
//        TextView contentView = (TextView) dialogView.findViewById(R.id.tv_content);
//        ImageView iv_icon = (ImageView) dialogView.findViewById(R.id.iv_icon);
//        if (iconId != 0 && iconId != -1) {
//            iv_icon.setImageResource(iconId);
//        }
//        if (content != null) {
//            contentView.setText(content);
//        }
//        dialogView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialogView.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if(onCancelListener!=null){
//                    onCancelListener.onClick(v);
//                }
//            }
//        });
//        dialogView.findViewById(R.id.submit).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//                if (onConfimListener != null) {
//                    onConfimListener.onClick(v);
//                }
//            }
//        });
//        dialog.show();
//        return dialog;
//    }
//
//
//    public static void setDialogBtnText(Dialog dialog, String submitStr, String cancelStr) {
//        TextView submit = (TextView) dialog.findViewById(R.id.submit);
//        TextView cancel = (TextView) dialog.findViewById(R.id.cancel);
//        submit.setText(submitStr);
//        cancel.setText(cancelStr);
//    }
//
//
//}
