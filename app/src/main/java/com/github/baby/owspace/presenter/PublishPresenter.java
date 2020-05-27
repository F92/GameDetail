package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.HomeList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class PublishPresenter implements PublishContract.presenter {
    private PublishContract.View view;
    private ApiService apiService;
    @Inject
    public PublishPresenter(PublishContract.View view,ApiService apiService){
        this.view = view;
        this.apiService = apiService;
    }
    @Override
    public void publish(String title, String content, String type, String gameName, String userName) {
        apiService.Publish(title,content,type,gameName,userName)
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
                        view.publishSuccess(s);
                    }
                });
    }
}
