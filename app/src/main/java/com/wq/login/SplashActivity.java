package com.wq.login;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.sunrun.sunrunframwork.adapter.ImagePagerAdapter;
import com.sunrun.sunrunframwork.common.DefaultMediaLoader;
import com.sunrun.sunrunframwork.uibase.BaseActivity;
import com.sunrun.sunrunframwork.utils.AHandler;
import com.sunrun.sunrunframwork.utils.AppUtils;
import com.sunrun.sunrunframwork.weight.AutoScrollViewPager;
import com.wq.base.LBaseActivity;
import com.wq.common.quest.Config;
import com.wq.common.util.IntentUtil;
import com.wq.project01.R;

import java.io.IOException;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 启动页(引导页)
 */
public class SplashActivity extends LBaseActivity {
    int startNum = 0;
    long time = 0,time2 = 2000;
    static SplashActivity act;
    AHandler.Task task,task2;
    @BindView(R.id.view_pager)
    AutoScrollViewPager viewPager;
    @BindView(R.id.start)
    View start;
    @BindView(R.id.iv_splash)
    ImageView ivSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        act = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_avtivity_splash);


//
//        Log.e("-----------" , "sHA1 = " + AppUtils.sHA1(that));
//        Log.e("-----------" , "getSingInfo = " +AppUtils.getSingInfo(that));


        startNum = Integer.parseInt(Config.getConfigInfo(this, Config.START_NUM, "0"));// 启动次数
        Config.putConfigInfo(this, Config.START_NUM, String.valueOf(startNum + 1));// 启动次数加1
//		setStatusBarTintColor(getColors(R.color.lucency));
		startNum=10;
        if (startNum <= 0) {
            try {
                viewPager.setVisibility(View.VISIBLE);
                viewPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_TO_PARENT);
                String[] bgs = getAssets().list(Config.START_IMG);
                Log.e("wsr", "bgs " + Arrays.asList(bgs).toString());
                for (int i = 0; i < bgs.length; i++) {
                    bgs[i] = Config.START_IMG + "/" + bgs[i];
                }
                viewPager.setAdapter(new ImagePagerAdapter<String>(this, Arrays.asList(bgs)) {
                    @Override
                    protected void loadImage(@NonNull ImageView imageView, @NonNull String s) {
                        DefaultMediaLoader.getInstance().displayImage(imageView,s,null);
                    }
                }.setInfiniteLoop(false));
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
//            NetImageUtil

//            ImageLoader.getInstance().displayImage("http://test.cnsunrun.com/zhaobao/Uploads/UserFile/Image/1/20170123/5885ab0484bed.jpg",ivSplash,NetImageUtil.getNoDefImgOption());
            AHandler.runTask(task = new AHandler.Task() {
                @Override
                public void update() {
                    startLogin(null);
                }
            }, time);
//            AHandler.runTask(task2 = new AHandler.Task() {
//                @Override
//                public void update() {
////                    ImageLoader.getInstance().loadImage("http://test.cnsunrun.com/zhaobao/Uploads/UserFile/Image/1/20170123/5885ab0484bed.jpg",NetImageUtil.getNoDefImgOption(),new SimpleImageLoadingListener(){
////                        @Override
////                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
////                            ivSplash.setImageBitmap(loadedImage);
////                            super.onLoadingComplete(imageUri, view, loadedImage);
////                        }
////                    });
////                    startLogin(null);
//                }
//            }, time2);

        }
    }

    public static SplashActivity getSplash() {
        return act;
    }

    public static void close() {
        if (act != null) {
            if (act.task == null) {
                act.finish();
            }
        }
    }

    protected void onDestroy() {
        super.onDestroy();
        act = null;
    }


    public void startLogin(View view) {
//		if (viewPager.getAdapter() != null && viewPager.getCurrentItem() != viewPager.getAdapter().getCount() - 1) { return; }
//		if (!isStart) {
//			isStart = true;
        if (!Config.getLoginInfo().isValid()) {
            IntentUtil.startLogin(that);
        } else {
            IntentUtil.startMainActivity(that);
        }
        finish();
    }

    @OnClick(R.id.start)
    public void onClick() {
        startLogin(null);
    }
}
