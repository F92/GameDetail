package com.github.baby.owspace.view.fragment;

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
import com.github.baby.owspace.di.components.DaggerDiscussComponent;
import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.di.modules.DiscussModule;
import com.github.baby.owspace.model.entity.ArticalList;
import com.github.baby.owspace.model.entity.DiscussContentList;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.DiscussNewList;
import com.github.baby.owspace.presenter.ArticalPresenter;
import com.github.baby.owspace.presenter.DiscussContract;
import com.github.baby.owspace.presenter.DiscussPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.view.adapter.DiscussNewsAdapter;
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

public class DiscussNewsFragment extends Fragment implements DiscussContract.View {

    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @Inject
    DiscussPresenter discussPresenter;

    private DiscussNewsAdapter discussNewsAdapter;
    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int lastVisibleItem;
    private String gameName;

    public static Fragment instance(String gameName) {
        Fragment fragment = new DiscussNewsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("gameName",gameName);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.discuss_list_layout, container, false);
        ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        final String gameName = getArguments().getString("gameName");
        this.gameName = gameName;
        initPresenter();
        initView();
    }

    private void initPresenter(){
        DaggerDiscussComponent.builder()
                .discussModule(new DiscussModule(this))
                .netComponent(OwspaceApplication.get(getActivity()).getNetComponent())
                .build()
                .inject(this);
    }
    private void loadData() {
        discussPresenter.getArtical(gameName);
    }

    private void initView() {


        deviceId = AppUtil.getDeviceId(getActivity());
        discussNewsAdapter = new DiscussNewsAdapter(getActivity());
        recycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        recycleView.addItemDecoration(new DividerItemDecoration(getActivity()));
        recycleView.setAdapter(discussNewsAdapter);
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
            discussNewsAdapter.setHasMore(false);
            discussNewsAdapter.notifyItemChanged(discussNewsAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateListQA(List<DiscussNewList> itemList) {

    }

    @Override
    public void updateListNews(List<ArticalList> itemList) {
        mPtrFrame.refreshComplete();
        if (isRefresh) {
            discussNewsAdapter.setHasMore(false);
            discussNewsAdapter.setError(true);
            isRefresh = false;
            discussNewsAdapter.replaceAllData(itemList);
            discussNewsAdapter.setError(false);
        } else {
            discussNewsAdapter.setArtList(itemList);
        }
    }

    @Override
    public void updateListDiscuss(List<DiscussNewList> itemList) {

    }

    @Override
    public void updateListContent(List<DiscussContentList> itemList) {

    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            discussNewsAdapter.setError(true);
            discussNewsAdapter.notifyItemChanged(discussNewsAdapter.getItemCount()-1);
        }else{
            Toast.makeText(getActivity(),"刷新失败",Toast.LENGTH_SHORT).show();
        }
    }
}
