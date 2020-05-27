package com.github.baby.owspace.di.modules;

import com.github.baby.owspace.presenter.DiscussContract;
import com.github.baby.owspace.presenter.PublishContract;

import dagger.Module;
import dagger.Provides;

@Module
public class PublishModule {
    private PublishContract.View mView;

    public PublishModule(com.github.baby.owspace.presenter.PublishContract.View mView) {
        this.mView = mView;
    }
    @Provides
    public PublishContract.View provideView(){
        return mView;
    }
}
