package com.github.baby.owspace.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.github.baby.owspace.view.adapter.ArtRecycleViewAdapter;
import com.github.baby.owspace.view.adapter.DiscussMainAdapter;
import com.github.baby.owspace.view.adapter.GameMainAdapter;
import com.github.baby.owspace.view.widget.CustomPtrHeader;
import com.github.baby.owspace.view.widget.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class GameFragment extends Fragment implements ArticalContract.View {
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

    private GameMainAdapter gameMainAdapter;
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
        presenter.getGameList();
    }

    private void searchData(String gameName){
        presenter.searchGame(gameName);
    }

    private void initView() {


        deviceId = AppUtil.getDeviceId(getActivity());
        gameMainAdapter = new GameMainAdapter(getActivity());
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recycleView.setAdapter(gameMainAdapter);
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
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                isRefresh = true;
                searchData(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
            gameMainAdapter.setHasMore(false);
            gameMainAdapter.notifyItemChanged(gameMainAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateListUI3(List<HomeList> itemList) {
        mPtrFrame.refreshComplete();
        gameMainAdapter.replaceAllData(itemList);
        gameMainAdapter.setHasMore(false);

    }

    @Override
    public void updateListUI(List<DiscussList> itemList) {

    }

    @Override
    public void updateListUI2(List<Item> itemList) {

    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            gameMainAdapter.setError(true);
            gameMainAdapter.notifyItemChanged(gameMainAdapter.getItemCount()-1);
        }else{
            Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
        }
    }
}
