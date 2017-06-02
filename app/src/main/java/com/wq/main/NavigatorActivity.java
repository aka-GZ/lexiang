package com.wq.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BadgeItem;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.wq.base.LBaseActivity;
import com.wq.mine.MineFragment;
import com.wq.project01.R;


/**
 * 主导航页
 */
public class NavigatorActivity extends LBaseActivity {


    @Override
    protected boolean isTranslucent() {
        return true;
    }

    @Override
    protected boolean isStatusContentDark() {
        return false;
    }

    Fragment[] fragments = new Fragment[4];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);
        initNavTabView();
        //设置页面加载的Fragment
        fragments[0] =new HomeFragment();
        fragments[1] = new HomeFragment();
        fragments[2] = new MineFragment();
        switchPanel(fragments[0]);
    }

    private Fragment currentActiveFragment;

    private void switchPanel(Fragment fragment) {
        if (fragment == currentActiveFragment) {
            return;
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (currentActiveFragment != null) {
            fragmentTransaction.hide(currentActiveFragment);
        }
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.mainContainer, fragment);
        } else {
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.commitNowAllowingStateLoss();
        currentActiveFragment = fragment;
    }

    private final BottomNavigationItem[] navbars = new BottomNavigationItem[4];
    private final BadgeItem[] badgeItems = new BadgeItem[4];

    private void initNavTabView() {
        final BottomNavigationBar navTabView = (BottomNavigationBar) findViewById(R.id.navTabView);

        //不设置InactiveIcon的情况下,会启用着色器自动着色,设置了则忽略,
        //Navbar 可以动态设置Badge的数字和显示以及隐藏,
        //但是一经创建,不可修改文字和图片,若非要改,需要调用initialise()重新布局
        navTabView.addItem(navbars[0] = new BottomNavigationItem(R.drawable.micon_index_select, "素材市场").setInactiveIconResource(R.drawable.micon_index));//;.setBadgeItem(badgeItems[0] = new BadgeItem().hide(false)));
        navTabView.addItem(navbars[1] = new BottomNavigationItem(R.drawable.micon_gains_select, "乐享圈").setInactiveIconResource(R.drawable.micon_gains));//.setBadgeItem(badgeItems[1] = new BadgeItem().hide(false)));
        navTabView.addItem(navbars[2] = new BottomNavigationItem(R.drawable.micon_mine_select, "个人中心").setInactiveIconResource(R.drawable.micon_mine));//.setBadgeItem(badgeItems[2] = new BadgeItem().hide(false)));
        navTabView.initialise();
        navTabView.setTabSelectedListener(new BottomNavigationBar.SimpleOnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {
                super.onTabSelected(position);
                switch (position) {
                    case 0:
                    case 2:
                    case 1:
                        switchPanel(fragments[position]);
                        break;
                }

            }

            @Override
            public void onTabReselected(int position) {
                super.onTabReselected(position);
            }

            @Override
            public void onTabUnselected(int position) {
                super.onTabUnselected(position);

            }
        });

    }
}
