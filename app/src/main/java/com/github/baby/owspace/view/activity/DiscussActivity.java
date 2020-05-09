package com.github.baby.owspace.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;


import com.github.baby.owspace.R;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.view.adapter.DiscussFragmentPaperAdapter;
import com.github.baby.owspace.view.fragment.DiscussNewsFragment;
import com.github.baby.owspace.view.fragment.DiscussNewFragment;
import com.github.baby.owspace.view.fragment.DiscussQAFragment;
import com.github.baby.owspace.view.fragment.MainFragment;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DiscussActivity extends BaseActivity {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.fabs)
    TabLayout fabs;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.title)
    TextView title;
    private ArrayList<Fragment> fragments;
    private DiscussFragmentPaperAdapter discussFragmentPaperAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discuss);
        ButterKnife.bind(this);

        Bundle bundle = getIntent().getExtras();
        DiscussList homeList = (DiscussList) bundle.getSerializable("discussList");

        initView();
        title.setText(homeList.getGameName());
        Fragment f1 = DiscussNewsFragment.instance(homeList.getGameName());
        Fragment f2 = DiscussNewFragment.instance(homeList.getGameName());
        Fragment f3 = DiscussQAFragment.instance(homeList.getGameName());

        fragments = new ArrayList<Fragment>();
        fragments.add(f1);
        fragments.add(f2);
        fragments.add(f3);
        discussFragmentPaperAdapter = new DiscussFragmentPaperAdapter(getSupportFragmentManager(),fragments);
        viewPager.setAdapter(discussFragmentPaperAdapter);

        fabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);


    }

    private void initView() {
        toolBar.setNavigationIcon(R.drawable.ic_back);
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

}
