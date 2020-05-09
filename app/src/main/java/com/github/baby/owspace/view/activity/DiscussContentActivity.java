package com.github.baby.owspace.view.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.baby.owspace.R;
import com.github.baby.owspace.app.GlideApp;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerArtComponent;
import com.github.baby.owspace.di.components.DaggerDiscussComponent;
import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.di.modules.DiscussModule;
import com.github.baby.owspace.model.entity.ArticalList;
import com.github.baby.owspace.model.entity.DiscussContentList;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.DiscussNewList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.ArticalContract;
import com.github.baby.owspace.presenter.ArticalPresenter;
import com.github.baby.owspace.presenter.DiscussContract;
import com.github.baby.owspace.presenter.DiscussPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.view.adapter.ArtRecycleViewAdapter;
import com.github.baby.owspace.view.adapter.DiscussContentAdapter;
import com.github.baby.owspace.view.widget.CustomPtrHeader;
import com.github.baby.owspace.view.widget.DividerItemDecoration;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/3
 * owspace
 */
public class DiscussContentActivity extends BaseActivity implements DiscussContract.View {
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    @BindView(R.id.image_iv)
    ImageView imageView;
    @BindView(R.id.user_tv)
    TextView userTv;
    @BindView(R.id.content_tv)
    TextView contentTv;
    @BindView(R.id.recycleView)
    RecyclerView recycleView;
    @BindView(R.id.ptrFrameLayout)
    PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.edit)
    EditText edit;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.discuss_title)
    RelativeLayout discussTitle;
    @Inject
    DiscussPresenter discussPresenter;
    private DiscussContentAdapter discussContentAdapter;
    private int page = 1;
    private int mode = 1;
    private boolean isRefresh;
    private boolean hasMore=true;
    private String deviceId;
    private int discussId;
    private int commentId;
    private int lastVisibleItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.discuss_content_layout);
        ButterKnife.bind(this);
        initPresenter();
        initView();
    }
    private void initPresenter(){
        DaggerDiscussComponent.builder()
                .discussModule(new DiscussModule(this))
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .build()
                .inject(this);

    }
    private void initView() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");

        Bundle bundle = getIntent().getExtras();
        DiscussList homeList = (DiscussList) bundle.getSerializable("discussList");

        discussId = homeList.getDiscussId();
        title.setText(homeList.getGameName());
        GlideApp.with(this).load(homeList.getUserImage()).centerCrop().into(imageView);
        userTv.setText(homeList.getUserName());
        contentTv.setText(homeList.getDiscussDetail());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String h="";
                String s="";
                try{
                    h = edit.getHint().toString();
                }catch (Exception e){

                }
                if("".equals(h)){

                }else {
                    s = h.substring(3);
                }
                String content = "";
                try {
                   content =  edit.getText().toString();
                }catch (Exception e){

                }
                if("".equals(content)){
                    Toast.makeText(getApplicationContext(),"请输入回复内容",Toast.LENGTH_LONG).show();
                }else{
                    System.out.println(commentId);
                    Reply("ubiboy",s,discussId,commentId,content);
                    edit.setText("");
                    edit.setHint("");
                    edit.clearFocus();
                    hideKeyboard();
                }


            }
        });
        discussTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edit.setText("");
                edit.setHint("");
                edit.clearFocus();
                hideKeyboard();
            }
        });

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        discussContentAdapter = new DiscussContentAdapter(this);
        recycleView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recycleView.addItemDecoration(new DividerItemDecoration(this));
        recycleView.setAdapter(discussContentAdapter);
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

    }

    private void loadData() {
        discussPresenter.getComment(discussId);
    }

    private void Reply(String userName, String replyName,int discussId,int rcommentId,String comment){
        discussPresenter.reply(userName, replyName,discussId,rcommentId,comment);
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
            discussContentAdapter.setHasMore(false);
            discussContentAdapter.notifyItemChanged(discussContentAdapter.getItemCount()-1);
        }
    }

    @Override
    public void updateListQA(List<DiscussNewList> itemList) {

    }

    @Override
    public void updateListNews(List<ArticalList> itemList) {

    }

    @Override
    public void updateListDiscuss(List<DiscussNewList> itemList) {

    }

    @Override
    public void updateListContent(List<DiscussContentList> itemList) {

        mPtrFrame.refreshComplete();
        if (isRefresh) {
            discussContentAdapter.setHasMore(false);
            discussContentAdapter.setError(true);
            isRefresh = false;
            discussContentAdapter.replaceAllData(itemList);
            discussContentAdapter.setError(false);
        } else {
            discussContentAdapter.setArtList(itemList);
        }
    }

    @Override
    public void showOnFailure() {
        if (!isRefresh){
            //显示失败
            discussContentAdapter.setError(true);
            discussContentAdapter.notifyItemChanged(discussContentAdapter.getItemCount()-1);
        }else{
            Toast.makeText(this,"刷新失败",Toast.LENGTH_SHORT).show();
        }
    }


    public void change(String userName,int commentId) {
        this.commentId = commentId;
        edit.requestFocus();
        edit.setHint("回复给"+userName);
        InputMethodManager imm= (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(edit, 0);
    }
    private void hideKeyboard(){
        InputMethodManager imm= (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(edit.getWindowToken(),0);
    }
}
