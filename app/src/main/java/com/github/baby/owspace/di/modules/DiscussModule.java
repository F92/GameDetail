package com.github.baby.owspace.di.modules;

import com.github.baby.owspace.presenter.ArticalContract;
import com.github.baby.owspace.presenter.DiscussContract;

import dagger.Module;
import dagger.Provides;

@Module
public class DiscussModule {
    private DiscussContract.View mView;

    public DiscussModule(DiscussContract.View mView) {
        this.mView = mView;
    }
    @Provides
    public DiscussContract.View provideView(){
        return mView;
    }
}
