package com.github.baby.owspace.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerSearchComponent;
import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.di.modules.SearchModule;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.ArticalContract;
import com.github.baby.owspace.presenter.ArticalPresenter;
import com.github.baby.owspace.presenter.SearchContract;
import com.github.baby.owspace.presenter.SearchPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.view.adapter.ArtRecycleViewAdapter;
import com.github.baby.owspace.view.adapter.SearchAdapter;
import com.github.baby.owspace.view.widget.CustomPtrHeader;
import com.github.baby.owspace.view.widget.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class SearchActivity extends BaseActivity implements SearchContract.View {
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.title)
    EditText title;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @Inject
    SearchPresenter presenter;
    private SearchAdapter recycleViewAdapter;
    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int lastVisibleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        mode = getIntent().getIntExtra("mode", 1);
        initPresenter();
        initView();
    }
    private void initPresenter(){
        DaggerSearchComponent.builder()
                .searchModule(new SearchModule(this))
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .build()
                .inject(this);

    }
    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        deviceId = AppUtil.getDeviceId(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        recycleViewAdapter = new SearchAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.addItemDecoration(new DividerItemDecoration(this));
        recycleView.setAdapter(recycleViewAdapter);

    }

    private void loadData(String gameName) {
        presenter.getDiscussList(gameName);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void showNoData() {
    }

    @Override
    public void showNoMore() {
    }

    @Override
    public void updateListUI(List<HomeList> itemList) {
        recycleViewAdapter.replaceAllData(itemList);
    }

    @Override
    public void updateListUI2(List<HomeList> itemList) {

    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            recycleViewAdapter.setError(true);
            recycleViewAdapter.notifyItemChanged(recycleViewAdapter.getItemCount()-1);
        }else{
            Toast.makeText(this,"刷新失败",Toast.LENGTH_SHORT).show();
        }
    }
}
