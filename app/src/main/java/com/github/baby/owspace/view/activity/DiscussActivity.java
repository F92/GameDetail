package com.github.baby.owspace.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

import com.github.baby.owspace.R;
import com.github.baby.owspace.view.adapter.DiscussFragmentPaperAdapter;
import com.github.baby.owspace.view.fragment.DiscussFragment;
import com.github.baby.owspace.view.fragment.DiscussNewsFragment;
import com.github.baby.owspace.view.fragment.NewDiscussFragment;
import com.github.baby.owspace.view.fragment.QAFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fabs)
    TabLayout fabs;
    private ArrayList<Fragment> fragments;
    private DiscussFragmentPaperAdapter discussFragmentPaperAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);

        fragments = new ArrayList<Fragment>();
        fragments.add(new DiscussNewsFragment());
        fragments.add(new NewDiscussFragment());
        fragments.add(new QAFragment());
        discussFragmentPaperAdapter = new DiscussFragmentPaperAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(discussFragmentPaperAdapter);

        fabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
    }
}
