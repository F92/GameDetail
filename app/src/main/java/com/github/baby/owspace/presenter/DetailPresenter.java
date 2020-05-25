package com.github.baby.owspace.presenter;


import android.widget.Toast;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.DetailEntity;
import com.github.baby.owspace.model.entity.Result;
import com.github.baby.owspace.view.activity.DetailActivity;

import javax.inject.Inject;

import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/9
 * owspace
 */
public class DetailPresenter implements DetailContract.Presenter {
    private DetailContract.View view;
    private ApiService apiService;

    @Inject
    public DetailPresenter(DetailContract.View view, ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getDetail(int gameId) {
        apiService.getArticalDetail(gameId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        view.updateListUI(s);
                    }
                });
    }

    @Override
    public void getArticalDetail(int articalId) {
        apiService.getDiscussArticalDetail(articalId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        view.updateListUI(s);
                    }
                });
    }

    @Override
    public void buy(String userName, String gameName) {
        apiService.buy(userName,gameName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        view.buySuccess(s);
                    }
                });
    }

}
