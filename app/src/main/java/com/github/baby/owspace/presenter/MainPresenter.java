package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;
import com.github.baby.owspace.model.entity.Result;
import com.github.baby.owspace.util.TimeUtil;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/2
 * owspace
 */
public class MainPresenter implements MainContract.Presenter {
    private MainContract.View view;
    private ApiService apiService;

    @Inject
    public MainPresenter(MainContract.View view,ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
        Logger.d("apppp:"+apiService);
    }


    @Override
    public void GetHomeList() {
        apiService.getHomeList()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
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
                        view.updateListUI(homeLists);

                    }
                });
    }
}
