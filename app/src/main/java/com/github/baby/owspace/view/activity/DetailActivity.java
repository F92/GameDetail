package com.github.baby.owspace.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.github.baby.owspace.R;
import com.github.baby.owspace.app.GlideApp;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerDetailComponent;
import com.github.baby.owspace.di.modules.DetailModule;
import com.github.baby.owspace.model.entity.DetailEntity;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.DetailContract;
import com.github.baby.owspace.presenter.DetailPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.util.tool.AnalysisHTML;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/9
 * owspace
 */
public class DetailActivity extends BaseActivity implements DetailContract.View, ObservableScrollViewCallbacks {
    @BindView(R.id.favorite)
    ImageView favorite;
    @BindView(R.id.write)
    ImageView write;
    @BindView(R.id.share)
    ImageView share;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.scrollView)
    ObservableScrollView scrollView;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.news_parse_web)
    LinearLayout newsParseWeb;
    @BindView(R.id.news_top_type)
    TextView newsTopType;
    @BindView(R.id.news_top_title)
    TextView newsTopTitle;
    @BindView(R.id.news_top_author)
    TextView newsTopAuthor;
    @BindView(R.id.news_top_lead)
    TextView newsTopLead;
    @BindView(R.id.news_top)
    LinearLayout newsTop;
    @BindView(R.id.news_top_img_under_line)
    View newsTopImgUnderLine;
    @BindView(R.id.news_top_lead_line)
    View newsTopLeadLine;
    @BindView(R.id.enter)
    Button enter;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.buy)
    Button buy;

    @Inject
    DetailPresenter presenter;
    private int mParallaxImageHeight;

    private int length;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_detail);
        ButterKnife.bind(this);
        initView();
        initPresenter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        final HomeList homeList = (HomeList) bundle.getSerializable("homeList");
        if (homeList != null){
            GlideApp.with(this).load(homeList.getGameImage()).centerCrop().into(image);
            newsTopLeadLine.setVisibility(View.VISIBLE);
            newsTopImgUnderLine.setVisibility(View.VISIBLE);
            newsTopType.setText("游 戏");
            newsTopTitle.setText(homeList.getGameName());
            newsTopAuthor.setText(homeList.getUserName());
            newsTopLead.setText(homeList.getGameIntroduce());
            newsTopLead.setLineSpacing(1.5f,1.8f);
            price.setText("价格："+homeList.getGamePrice()+"元");
            final DiscussList discussList = new DiscussList();
            discussList.setGameName(homeList.getGameName());
            enter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(DetailActivity.this,DiscussActivity.class);
                    intent.putExtra("discussList",discussList);
                    startActivity(intent);

                }
            });
            buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    presenter.buy("UBIboy",homeList.getGameName());
                }
            });
            presenter.getDetail(homeList.getGameId());
            this.length = homeList.getGameIntroduce().trim().length();
        }

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void initPresenter(){
        DaggerDetailComponent.builder()
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .detailModule(new DetailModule(this))
                .build()
                .inject(this);
    }
    private void initView() {
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(0, getResources().getColor(R.color.primary)));
        scrollView.setScrollViewCallbacks(this);
        mParallaxImageHeight = getResources().getDimensionPixelSize(R.dimen.parallax_image_height);
    }

    private void initWebViewSetting() {
        WebSettings localWebSettings = this.webView.getSettings();
        localWebSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        localWebSettings.setJavaScriptEnabled(true);
        localWebSettings.setSupportZoom(true);
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        localWebSettings.setUseWideViewPort(true);
        localWebSettings.setLoadWithOverviewMode(true);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }

    @Override
    public void updateListUI(String detail) {
        AnalysisHTML analysisHTML = new AnalysisHTML();
        analysisHTML.loadHtml(this, detail, analysisHTML.HTML_STRING, newsParseWeb, this.length);
        newsTopType.setText("游 戏");

    }

    @Override
    public void showOnFailure() {

    }

    @Override
    public void buySuccess(String info) {
        Toast.makeText(this,info,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onScrollChanged(int i, boolean b, boolean b1) {
        int baseColor = getResources().getColor(R.color.primary);
        float alpha = Math.min(1, (float) i / mParallaxImageHeight);
        toolBar.setBackgroundColor(ScrollUtils.getColorWithAlpha(alpha, baseColor));
//        ViewHelper.setTranslationY(image, i / 2);
    }

    @Override
    public void onDownMotionEvent() {

    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {

    }

    public String addParams2WezeitUrl(String url, boolean paramBoolean) {
        StringBuffer localStringBuffer = new StringBuffer();
        localStringBuffer.append(url);
        localStringBuffer.append("?client=android");
        localStringBuffer.append("&device_id=" + AppUtil.getDeviceId(this));
        localStringBuffer.append("&version=" + "1.3.0");
        if (paramBoolean)
            localStringBuffer.append("&show_video=0");
        else {
            localStringBuffer.append("&show_video=1");
        }
        return localStringBuffer.toString();
    }

}
