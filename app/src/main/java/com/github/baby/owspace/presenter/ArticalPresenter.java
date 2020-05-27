package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.model.entity.Result;
import com.github.baby.owspace.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/3
 * owspace
 */
public class ArticalPresenter implements ArticalContract.Presenter{

    private ArticalContract.View view;
    private ApiService apiService;
    @Inject
    public ArticalPresenter(ArticalContract.View view,ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getListByPage(int page, int model, String pageId, String deviceId, String createTime) {
        apiService.getList("api","getList",page,model,pageId,createTime,"android","1.3.0", TimeUtil.getCurrentSeconds(), deviceId,1)
               .subscribeOn(Schedulers.io())
               .observeOn(AndroidSchedulers.mainThread())
               .subscribe(new Subscriber<Result.Data<List<Item>>>() {
                   @Override
                   public void onCompleted() {

                   }

                   @Override
                   public void onError(Throwable e) {
                       view.showOnFailure();
                   }

                   @Override
                   public void onNext(Result.Data<List<Item>> listData) {
                       int size = listData.getDatas().size();
                       if(size>0){
                           view.updateListUI2(listData.getDatas());
                       }else {
                           view.showNoMore();
                       }
                   }
               });
    }

    @Override
    public void getDiscussList() {
        apiService.getDiscussList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<DiscussList>homeLists = new Gson().fromJson(s, new TypeToken<List<DiscussList>>(){}.getType());
                        view.updateListUI(homeLists);
                    }
                });
    }

    @Override
    public void getGameList() {
        apiService.getHomeList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<HomeList>homeLists = new Gson().fromJson(s, new TypeToken<List<HomeList>>(){}.getType());
                        view.updateListUI3(homeLists);
                    }
                });
    }

    @Override
    public void searchGame(String gameName) {
        apiService.SearchDiscuss(gameName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<HomeList> homeLists = new Gson().fromJson(s, new TypeToken<List<HomeList>>(){}.getType());
                        view.updateListUI3(homeLists);
                    }
                });
    }
}
