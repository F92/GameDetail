package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;

import javax.inject.Inject;

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
    public void getDiscussList() {

    }
}
