package com.github.baby.owspace.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.LinearLayout;

import java.util.List;

public class DiscussFragmentPaperAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    public DiscussFragmentPaperAdapter(FragmentManager fm,List<Fragment> list) {
        super(fm);
        this.fragmentList = list;
        fragmentManager = fm;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentList.get(i);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
