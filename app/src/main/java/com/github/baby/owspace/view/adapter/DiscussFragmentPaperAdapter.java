package com.github.baby.owspace.view.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DiscussFragmentPaperAdapter extends FragmentPagerAdapter {
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private String[] title = {"更新","讨论","提问"};
    private List<String> titlelist = new ArrayList<>();
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

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        for (int i=0;i<3;i++){
            titlelist.add(title[i]);
        }
        return titlelist.get(position);
    }
}
