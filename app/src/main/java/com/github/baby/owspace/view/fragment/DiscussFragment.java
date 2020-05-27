package com.github.baby.owspace.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerArtComponent;
import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.ArticalContract;
import com.github.baby.owspace.presenter.ArticalPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.view.activity.SearchActivity;
import com.github.baby.owspace.view.adapter.ArtRecycleViewAdapter;
import com.github.baby.owspace.view.adapter.DiscussMainAdapter;
import com.github.baby.owspace.view.widget.CustomPtrHeader;
import com.github.baby.owspace.view.widget.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class DiscussFragment extends Fragment implements ArticalContract.View {

    @BindView(R.id.title)
    EditText editText;
    @BindView(R.id.search)
    ImageView search;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @Inject
    ArticalPresenter presenter;

    private DiscussMainAdapter discussMainAdapter;
    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int lastVisibleItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_list_layout,container,false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    Intent intent;
                    intent = new Intent(getActivity(), SearchActivity.class);
                    startActivity(intent);
                }
            }
        });
        Intent intent = getActivity().getIntent();
        mode = intent.getIntExtra("mode", 1);
        initPresenter();
        initView();


    }

    private void initPresenter(){
        DaggerArtComponent.builder()
                .artModule(new ArtModule(this))
                .netComponent(OwspaceApplication.get(getActivity()).getNetComponent())
                .build()
                .inject(this);

    }
    private void loadData() {
        presenter.getDiscussList();
    }

    private void initView() {


        deviceId = AppUtil.getDeviceId(getActivity());
        discussMainAdapter = new DiscussMainAdapter(getActivity());
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recycleView.setAdapter(discussMainAdapter);
        mPtrFrame.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page=1;
                isRefresh=true;
                hasMore = true;
                loadData();
            }
        });
        mPtrFrame.setOffsetToRefresh(200);
        mPtrFrame.autoRefresh(true);
        CustomPtrHeader header = new CustomPtrHeader(getActivity(),mode);
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                lastVisibleItem = ((LinearLayoutManager) recyclerView.getLayoutManager()).findLastVisibleItemPosition();
            }
        });
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
        hasMore = false;
        if (!isRefresh){
            //显示没有更多
            discussMainAdapter.setHasMore(false);
            discussMainAdapter.notifyItemChanged(discussMainAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateListUI3(List<HomeList> itemList) {

    }

    @Override
    public void updateListUI(List<DiscussList> itemList) {
        mPtrFrame.refreshComplete();
        discussMainAdapter.replaceAllData(itemList);
    }

    @Override
    public void updateListUI2(List<Item> itemList) {

    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            discussMainAdapter.setError(true);
            discussMainAdapter.notifyItemChanged(discussMainAdapter.getItemCount()-1);
        }else{
            Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
        }
    }
}
