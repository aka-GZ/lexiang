package com.wq.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wq.project01.R;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.ref.WeakReference;

/**
 * @作者: Liufan
 * @时间: 2017/5/15
 * @功能描述:
 */


public class TitleBar extends FrameLayout {
    private View btnLeft;
    private ImageView imgLeft;
    private TextView txtLeft;

    private TextView txtTitle;

    private View btnRight;
    private ImageView imgRight;
    private TextView txtRight;

    private View btnRight2;
    private ImageView imgRight2;
    private TextView txtRight2;



    private View bottomLine;

    public TitleBar(@NonNull Context context) {
        this(context, null);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public TitleBar(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflateRootView();
        initViews();
        setAttrs(context, attrs);
        setTitleBarHeightToWrapStatusBar();
    }

    private void setTitleBarHeightToWrapStatusBar() {
        if (fitStatusBar && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            post(new Runnable() {
                @Override
                public void run() {
                    int statusBarHeight = getStatusBarHeight(getContext());
                    setPadding(0, statusBarHeight, 0, 0);
                    ViewGroup.LayoutParams lp = getLayoutParams();
                    lp.height += statusBarHeight;
                    setLayoutParams(lp);
                }
            });
        }
    }

    private static final int STATUS_HEIGHT_DP = 20;

    public int getStatusBarHeight(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, STATUS_HEIGHT_DP, context.getResources().getDisplayMetrics());
        }
        return 0;
    }

    private boolean fitStatusBar;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    private void setAttrs(Context context, AttributeSet attrs) {
        int defaultTextColor = context.getResources().getColor(R.color.white);
        TypedArray att = context.obtainStyledAttributes(attrs, R.styleable.TitleBar);
        //
        TypedArray typedArray = context.obtainStyledAttributes(new int[]{R.attr.colorPrimary});
        int c = typedArray.getColor(0, Color.WHITE);
        final String titleText = att.getString(R.styleable.TitleBar_title);
        final int titleColor = att.getColor(R.styleable.TitleBar_titleColor, defaultTextColor);
        final int backgroundColor = att.getColor(R.styleable.TitleBar_backgroundColor, c);
        final Drawable backgroundRes = att.getDrawable(R.styleable.TitleBar_backgroundDrawable);
        if (backgroundRes != null) {
            setBackgroundDrawable(backgroundRes);
        }else{
            setBackgroundColor(backgroundColor);
        }

        fitStatusBar = att.getBoolean(R.styleable.TitleBar_fitStatusBar, true);

        setTitle(titleText, titleColor);


        final int leftIcon = att.getResourceId(R.styleable.TitleBar_leftIcon, -1);
        final String leftText = att.getString(R.styleable.TitleBar_leftText);
        final int leftTextColor = att.getColor(R.styleable.TitleBar_leftTextColor, defaultTextColor);
        final boolean leftAsFinish = att.getBoolean(R.styleable.TitleBar_leftAsFinish, false);
        final String leftVisible = att.getString(R.styleable.TitleBar_leftVisible);
        setLeftVisible(TextUtils.equals("1", leftVisible) ? View.VISIBLE : View.GONE);
        setLeft(leftIcon, leftText, leftTextColor, (leftAsFinish && (getContext() instanceof Activity)) ? new FinishAction((Activity) getContext()) : null);
        final int rightIcon = att.getResourceId(R.styleable.TitleBar_rightIcon, -1);
        final String rightText = att.getString(R.styleable.TitleBar_rightText);
        final int rightTextColor = att.getColor(R.styleable.TitleBar_rightTextColor, defaultTextColor);
        final String rightVisible = att.getString(R.styleable.TitleBar_rightVisible);
        setRight(rightIcon, rightText, rightTextColor, null);

        final int rightIcon2 = att.getResourceId(R.styleable.TitleBar_rightIcon2, -1);
        final String rightText2 = att.getString(R.styleable.TitleBar_rightText2);
        final int rightTextColor2 = att.getColor(R.styleable.TitleBar_rightTextColor2, defaultTextColor);
        final String rightVisible2 = att.getString(R.styleable.TitleBar_rightVisible2);
        setRight2(rightIcon2, rightText2, rightTextColor2, null);



        Drawable drawable = att.getDrawable(R.styleable.TitleBar_bottomDivider);
        int dividerHeight = att.getDimensionPixelSize(R.styleable.TitleBar_bottomDividerHeight, 2);

        if (drawable != null) {
            bottomLine.setBackgroundDrawable(drawable);
        }
        if (!isInEditMode()) {
            ViewGroup.LayoutParams layoutParams = bottomLine.getLayoutParams();
            if (layoutParams == null) {
                layoutParams = generateDefaultLayoutParams();
            }
            layoutParams.height = dividerHeight;
        }
        att.recycle();
        att = null;


        setRightVisible(TextUtils.equals("1", rightVisible) ? View.VISIBLE : View.GONE);
        setRight2Visible(TextUtils.equals("1", rightVisible) ? View.VISIBLE : View.GONE);
    }

    private <T extends View> T get(@IdRes int id) {
        return (T) findViewById(id);
    }

    private void initViews() {
        btnLeft = get(R.id.btnLeft);
        imgLeft = get(R.id.imgLeft);
        txtLeft = get(R.id.txtLeft);

        txtTitle = get(R.id.txtTitle);

        btnRight = get(R.id.btnRight);
        imgRight = get(R.id.imgRight);
        txtRight = get(R.id.txtRight);

        btnRight2 = get(R.id.btnRight2);
        imgRight2 = get(R.id.imgRight2);
        txtRight2 = get(R.id.txtRight2);

        bottomLine = get(R.id.bottomLine);
    }

    public TitleBar setTitle(@NonNull String title) {
        return setTitle(title, Color.TRANSPARENT);
    }

    public TitleBar setTitle(String title, @ColorInt int textColor) {
        if (textColor != Color.TRANSPARENT) {
            this.txtTitle.setTextColor(textColor);
        }
        this.txtTitle.setText(title);
        return this;
    }

    public void setLeftVisible(@Visibility int visibility) {
        this.btnLeft.setVisibility(visibility);
    }

    public void setRightVisible(@Visibility int visibility) {
        this.btnRight.setVisibility(visibility);
    }


    public void setRight2Visible(@Visibility int visibility) {
        this.btnRight2.setVisibility(visibility);
    }

    public TitleBar setLeftAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        btnLeft.setVisibility(View.VISIBLE);
        this.btnLeft.setOnClickListener(click);
        return this;
    }

    public TitleBar setLeftIcon(@DrawableRes int icon) {
        if (icon == -1) {
            MarginLayoutParams layoutParams = (MarginLayoutParams) txtLeft.getLayoutParams();
            if(layoutParams != null) {
                layoutParams.leftMargin = 0;
            }

            btnLeft.setVisibility(GONE);
            return this;
        }
        btnLeft.setVisibility(View.VISIBLE);
        this.imgLeft.setImageResource(icon);
        return this;
    }

    public TitleBar setLeftText(@NonNull String leftText) {
        if (TextUtils.isEmpty(leftText)) return this;
        btnLeft.setVisibility(View.VISIBLE);
        this.txtLeft.setText(leftText);
        return this;
    }

    public TitleBar setLeft(@DrawableRes int icon, @NonNull String text, @ColorInt int color, @NonNull OnClickListener click) {
        return this.setLeftIcon(icon).setLeftText(text).setLeftTextColor(color).setLeftAction(click);
    }

    private TitleBar setLeftTextColor(int color) {
        this.txtLeft.setTextColor(color);
        return this;
    }

    public TitleBar setRightAction2(@NonNull OnClickListener click) {
        if (click == null) return this;
        btnRight2.setVisibility(View.VISIBLE);
        this.btnRight2.setOnClickListener(click);
        return this;
    }

    public TitleBar setRightAction(@NonNull OnClickListener click) {
        if (click == null) return this;
        btnRight.setVisibility(View.VISIBLE);
        this.btnRight.setOnClickListener(click);
        return this;
    }


    public TitleBar setRightIcon2(@DrawableRes int icon) {
        if (icon == -1) return this;
        btnRight2.setVisibility(View.VISIBLE);
        this.imgRight2.setImageResource(icon);
        return this;
    }

    public TitleBar setRightIcon(@DrawableRes int icon) {
        if (icon == -1) return this;
        btnRight.setVisibility(View.VISIBLE);
        this.imgRight.setImageResource(icon);
        return this;
    }

    public TitleBar setRightText2(@NonNull String rightText) {
        if (TextUtils.isEmpty(rightText)) return this;
        btnRight2.setVisibility(View.VISIBLE);
        this.txtRight2.setText(rightText);
        return this;
    }

    public TitleBar setRightText(@NonNull String rightText) {
        if (TextUtils.isEmpty(rightText)) return this;
        btnRight.setVisibility(View.VISIBLE);
        this.txtRight.setText(rightText);
        return this;
    }

    public TitleBar setRight2(@DrawableRes int icon, @NonNull String text, @ColorInt int color, @NonNull OnClickListener click) {
        return this.setRightIcon2(icon).setRightText2(text).setRightTextColor2(color).setRightAction2(click);
    }

    public TitleBar setRight(@DrawableRes int icon, @NonNull String text, @ColorInt int color, @NonNull OnClickListener click) {
        return this.setRightIcon(icon).setRightText(text).setRightTextColor(color).setRightAction(click);
    }

    private TitleBar setRightTextColor2(int color) {
        this.txtRight2.setTextColor(color);
        return this;
    }

    private TitleBar setRightTextColor(int color) {
        this.txtRight.setTextColor(color);
        return this;
    }

    private void inflateRootView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        layoutInflater.inflate(R.layout.view_titlebar_simple, this, true);
    }

    public TitleBar build(@NonNull Activity context, @NonNull String title, boolean hasLeftAction) {
        return build(context, title, -1, null, hasLeftAction ? new FinishAction(context) : null);
    }

    public TitleBar build(@NonNull Activity context, @NonNull String title, String leftText) {
        return build(context, title, -1, leftText, new FinishAction(context));
    }

    public TitleBar build(@NonNull Activity context, @NonNull String title, @DrawableRes int leftIcon, String leftText) {
        return build(context, title, leftIcon, leftText, new FinishAction(context));
    }

    public TitleBar build(@NonNull Activity context, @NonNull String title, @DrawableRes int leftIcon, String leftText, OnClickListener leftActoin) {
        if (!TextUtils.isEmpty(leftText)) {
            setLeftText(leftText);
        }
        if (leftIcon != -1) {
            setLeftAction(leftActoin);
        }
        if (leftActoin != null) {
            setLeftAction(leftActoin);
        }
        setTitle(title);
        return this;
    }


    class FinishAction implements OnClickListener {

        private WeakReference<Activity> context;

        public FinishAction(Activity act) {
            this.context = new WeakReference<Activity>(act);
        }

        @Override
        public void onClick(View v) {
            Activity activity = context.get();
            if (activity != null) {
                activity.finish();
            }
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    @Target(ElementType.PARAMETER)
    @IntDef({VISIBLE, GONE})
    @interface Visibility {

    }
}
