package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.ArticalList;
import com.github.baby.owspace.model.entity.HomeList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private ApiService apiService;

    @Inject
    public SearchPresenter(SearchContract.View view,ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }
    @Override
    public void getGameList() {

    }

    @Override
    public void getDiscussList(String gameName) {
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
                        view.updateListUI(homeLists);
                    }
                });

    }
}
