//package com.wq.common.util;
//
//import android.content.Context;
//import android.text.TextUtils;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//
//import com.chad.library.adapter.base.BaseQuickAdapter;
//import com.sunrun.sunrunframwork.weight.pulltorefresh.PullToRefreshListView;
//
//import cn.cnsunrun.shangshengxinghuo.R;
//
//import static com.sunrun.sunrunframwork.utils.EmptyDeal.empty;
//
//
///**
// * @功能描述: 获取内容为空的界面
// */
//
//public class GetEmptyViewUtils {
//
//
//    /**
//     * 获取通用图标
//     *
//     * @param context
//     * @return
//     */
//    public static View getCommentEmpty(Context context, String text) {
//        return GetEmptyView(context, R.drawable.ic_empty_message, TextUtils.isEmpty(text) ? "没有内容哦" : text);
//    }
//
//    /**
//     * 获取内容为空的界面
//     *
//     * @param context
//     * @param imgIds
//     * @param emptyString
//     * @return
//     */
//    public static View GetEmptyView(Context context, int imgIds, String emptyString) {
//        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_list_empty_view, null);
//        ImageView imageView = (ImageView) rootView.findViewById(R.id.iv_empty);
//        imageView.setImageResource(imgIds);
//        TextView textView = (TextView) rootView.findViewById(R.id.tv_empty_text);
//        textView.setText(emptyString);
//        return rootView;
//    }
//
//    /**
//     *
//     * 为列表绑定空视图
//     * @param imgIds
//     * @param emptyString
//     * @return
//     */
//    public static View bindEmptyView(PullToRefreshListView refreshListView, int imgIds, String emptyString, boolean isEmpty) {
//        View emptyView = refreshListView.getRefreshableView().getEmptyView();
//        if (emptyView == null) {
//            emptyView = LayoutInflater.from(refreshListView.getContext()).inflate(R.layout.layout_list_empty_view, null);
//            refreshListView.setEmptyView(emptyView);
//        }
//        switchEmptyView(emptyView, imgIds, emptyString, isEmpty);
//        return emptyView;
//    }
//    /**
//     *
//     * 为列表绑定空视图
//     * @param imgIds
//     * @param emptyString
//     * @return
//     */
//    public static View bindEmptyView(ListView refreshListView, int imgIds, String emptyString, boolean isEmpty) {
//        View emptyView = refreshListView.getEmptyView();
//        if (emptyView == null) {
//            emptyView = LayoutInflater.from(refreshListView.getContext()).inflate(R.layout.layout_list_empty_view, null);
//            refreshListView.setEmptyView(emptyView);
//        }
//        switchEmptyView(emptyView, imgIds, emptyString, isEmpty);
//        return emptyView;
//    }
//
//    /**
//     *
//     * 为列表绑定空视图
//     * @param imgIds
//     * @param emptyString
//     * @return
//     */
//    public static View bindEmptyView(Context mContext,BaseQuickAdapter adapter, int imgIds, String emptyString, boolean isEmpty) {
//
//        View emptyView = adapter.getEmptyView();
//        if (emptyView == null) {
//            emptyView = LayoutInflater.from(mContext).inflate(R.layout.layout_list_empty_view, null);
//            adapter.setEmptyView(emptyView);
//        }
//        switchEmptyView(emptyView, imgIds, emptyString, isEmpty);
//        return emptyView;
//    }
//    /**
//     *
//     * 为列表绑定空视图
//     * @param imgIds
//     * @param emptyString
//     * @return
//     */
//    public static View bindSearchEmptyView(PullToRefreshListView refreshListView, int imgIds, String emptyString, String emptyTipString, boolean isEmpty) {
//        View emptyView = refreshListView.getRefreshableView().getEmptyView();
//        if (emptyView == null) {
//            emptyView = LayoutInflater.from(refreshListView.getContext()).inflate(R.layout.layout_list_empty_view, null);
//            refreshListView.setEmptyView(emptyView);
//        }
//        switchSearchEmptyView(emptyView, imgIds, emptyString,emptyTipString, isEmpty);
//        return emptyView;
//    }
//
//
//    public static void switchSearchEmptyView(View emptyView, int imgIds, String emptyString,String tipString, boolean isEmpty) {
//        ImageView imageView = (ImageView) emptyView.findViewById(R.id.iv_empty);
//        if(imgIds!=0) {
//            imageView.setImageResource(imgIds);
//        }
//        TextView textView = (TextView) emptyView.findViewById(R.id.tv_empty_text);
//        textView.setText(emptyString);
//        TextView tipView = (TextView) emptyView.findViewById(R.id.tv_empty_tip_text);
//        tipView.setText(tipString);
//        tipView.setVisibility(!empty(tipString) ? View.VISIBLE : View.GONE);
//        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
//    }
//    public static void switchEmptyView(View emptyView, int imgIds, String emptyString, boolean isEmpty) {
//        ImageView imageView = (ImageView) emptyView.findViewById(R.id.iv_empty);
//        if(imgIds!=0) {
//            imageView.setImageResource(imgIds);
//        }
//        TextView textView = (TextView) emptyView.findViewById(R.id.tv_empty_text);
//        textView.setText(emptyString);
//        emptyView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
//    }
//
//    /**
//     * 切换显示状态,
//     * @param isEmpty 是否为空
//     * @param needShowViews 为空是需要展示的视图
//     */
//    public static void switchEmptyView( boolean isEmpty,View...needShowViews){
//        for (View needShowView : needShowViews) {
//            needShowView.setVisibility(isEmpty?View.VISIBLE:View.GONE);
//        }
//    }
//
//}
