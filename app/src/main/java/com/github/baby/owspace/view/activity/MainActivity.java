package com.github.baby.owspace.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bumptech.glide.Glide;
import com.github.baby.owspace.R;
import com.github.baby.owspace.app.OwspaceApplication;
import com.github.baby.owspace.di.components.DaggerMainComponent;
import com.github.baby.owspace.di.modules.MainModule;
import com.github.baby.owspace.model.entity.Event;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.presenter.MainContract;
import com.github.baby.owspace.presenter.MainPresenter;
import com.github.baby.owspace.util.AppUtil;
import com.github.baby.owspace.util.PreferenceUtils;
import com.github.baby.owspace.util.TimeUtil;
import com.github.baby.owspace.util.tool.RxBus;
import com.github.baby.owspace.view.adapter.VerticalPagerAdapter;
import com.github.baby.owspace.view.fragment.ArtFragment;
import com.github.baby.owspace.view.fragment.DiscussFragment;
import com.github.baby.owspace.view.fragment.LeftMenuFragment;
import com.github.baby.owspace.view.fragment.RightMenuFragment;
import com.github.baby.owspace.view.widget.LunarDialog;
import com.github.baby.owspace.view.widget.VerticalViewPager;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.orhanobut.logger.Logger;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscription;
import rx.functions.Action1;

public class MainActivity extends BaseActivity implements MainContract.View {

    @BindView(R.id.view_pager)
    VerticalViewPager viewPager;

    @BindView(R.id.main_fm2)
    LinearLayout linearLayout;

    private SlidingMenu slidingMenu;
    private LeftMenuFragment leftMenu;
    private RightMenuFragment rightMenu;
    @Inject
    MainPresenter presenter;
    private VerticalPagerAdapter pagerAdapter;

    private int page = 1;
    private boolean isLoading = true;
    private long mLastClickTime;

    private Subscription subscription;

    private String deviceId;

    private AppBarConfiguration mappBarConfiguration;

    private LeftMenuFragment leftMenuFragment;
    private ArtFragment artFragment;
    private DiscussFragment discussFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        intfragment();
        initMenu();
        initPage();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.navigation_home:
                        fragment(R.id.navigation_home);
                        return true;
                    case  R.id.navigation_dashboard:
                        fragment(R.id.navigation_dashboard);
                        return true;
                    case R.id.navigation_notifications:
                        fragment(R.id.navigation_notifications);
                        return true;

                }
                return false;
            }
        });



        deviceId = AppUtil.getDeviceId(this);
        String getLunar= PreferenceUtils.getPrefString(this,"getLunar",null);
        if (!TimeUtil.getDate("yyyyMMdd").equals(getLunar)){
            loadRecommend();
        }
        loadData(1, 0, "0", "0");



    }

    /**
     * 初始化fragment
     */
    private void intfragment(){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        leftMenuFragment = new LeftMenuFragment();
        artFragment = new ArtFragment();
        discussFragment = new DiscussFragment();
        transaction.add(R.id.main_fm2,artFragment,"art").hide(artFragment).add(R.id.main_fm2, discussFragment,"discuss").hide(discussFragment);

        transaction.commit();
    }

    /**
     * 底部导航栏
     * @param id
     */
    private void fragment(int id){

        if(id == R.id.navigation_dashboard){
            viewPager.setVisibility(View.INVISIBLE);
            FragmentManager fm1 = getSupportFragmentManager();
            FragmentTransaction transaction1 = fm1.beginTransaction();
            transaction1.show(discussFragment).hide(artFragment).commit();
        }else if(id == R.id.navigation_home){
            FragmentManager fm2 = getSupportFragmentManager();
            FragmentTransaction transaction2 = fm2.beginTransaction();
            transaction2.hide(discussFragment).hide(artFragment).commit();
            viewPager.setVisibility(View.VISIBLE);
        }else if(id == R.id.navigation_notifications){
            viewPager.setVisibility(View.INVISIBLE);
            FragmentManager fm3 = getSupportFragmentManager();
            FragmentTransaction transaction3 = fm3.beginTransaction();
            transaction3.hide(discussFragment).show(artFragment).commit();
        }
    }

    /**
     * 初始化侧边栏
     */
    private void initMenu() {
        slidingMenu = new SlidingMenu(this);
        slidingMenu.setMode(SlidingMenu.LEFT);
        // 设置触摸屏幕的模式
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        // 设置渐入渐出效果的值
        slidingMenu.setFadeDegree(0.35f);
        slidingMenu.setFadeEnabled(true);
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        slidingMenu.setMenu(R.layout.right_menu);
        rightMenu = new RightMenuFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.right_menu, rightMenu).commit();
        subscription = RxBus.getInstance().toObservable(Event.class)
                .subscribe(new Action1<Event>() {
                    @Override
                    public void call(Event event) {
                        slidingMenu.showContent();
                    }
                });
    }

    /**
     * 初始化首页
     */
    private void initPage() {
        pagerAdapter = new VerticalPagerAdapter(getSupportFragmentManager());
        DaggerMainComponent.builder().
                mainModule(new MainModule(this))
                .netComponent(OwspaceApplication.get(this).getNetComponent())
                .build()
                .inject(this);
//        presenter = new MainPresenter(this);
//        viewPager.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (pagerAdapter.getCount() <= position + 2 && !isLoading) {
                    if (isLoading){
                        Toast.makeText(MainActivity.this,"正在努力加载...",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    Logger.i("page=" + page + ",getLastItemId=" + pagerAdapter.getLastItemId());
                    loadData(page, 0, pagerAdapter.getLastItemId(), pagerAdapter.getLastItemCreateTime());
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    private void loadData(int page, int mode, String pageId, String createTime) {
        isLoading = true;
        presenter.getListByPage(page, mode, pageId, deviceId, createTime);
    }
    private void loadRecommend(){
        presenter.getRecommend(deviceId);
    }

    @Override
    public void onBackPressed() {
        if (slidingMenu.isMenuShowing() || slidingMenu.isSecondaryMenuShowing()) {
            slidingMenu.showContent();
        } else {
            if (System.currentTimeMillis() - mLastClickTime <= 2000L) {
                super.onBackPressed();
            } else {
                mLastClickTime = System.currentTimeMillis();
                Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            }

        }

    }

    @OnClick({R.id.left_slide})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_slide:
                slidingMenu.showMenu();
                rightMenu.startAnim();
                break;

        }
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
        Toast.makeText(this, "没有更多数据了", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void updateListUI(List<Item> itemList) {
        isLoading = false;
        pagerAdapter.setDataList(itemList);
        page++;
    }

    @Override
    public void showOnFailure() {
//        if (pagerAdapter.getCount() == 0) {
//            showNoData();
//        }
        Toast.makeText(this, "加载数据失败，请检查您的网络", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLunar(String content) {
        Logger.e("showLunar:"+content);
        PreferenceUtils.setPrefString(this,"getLunar",TimeUtil.getDate("yyyyMMdd"));
        LunarDialog lunarDialog = new LunarDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_lunar,null);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_iv);
        Glide.with(this).load(content).into(imageView);
        lunarDialog.setContentView(view);
        lunarDialog.show();
    }

    @Override
    protected void onDestroy() {
        if (subscription.isUnsubscribed()){
            subscription.unsubscribe();
        }
        super.onDestroy();
    }
}
