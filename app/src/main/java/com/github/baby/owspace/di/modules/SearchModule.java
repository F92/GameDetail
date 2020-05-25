package com.github.baby.owspace.di.modules;

import com.github.baby.owspace.presenter.DiscussContract;
import com.github.baby.owspace.presenter.SearchContract;

import dagger.Module;
import dagger.Provides;

@Module
public class SearchModule {
    private SearchContract.View mView;

    public SearchModule(SearchContract.View mView) {
        this.mView = mView;
    }
    @Provides
    public SearchContract.View provideView(){
        return mView;
    }
}
