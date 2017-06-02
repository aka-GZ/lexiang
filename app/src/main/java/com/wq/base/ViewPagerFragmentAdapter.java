package com.wq.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragmentAdapter extends FragmentPagerAdapter {
    private List<? extends Fragment> fragments = new ArrayList<>();

    public ViewPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return fragments.get(arg0);
    }


    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return fragments.size();
    }


    public List<? extends Fragment> getFragments() {
        return fragments;
    }

    public void setFragments(List<? extends Fragment> fragments) {
        this.fragments = fragments;
    }

}