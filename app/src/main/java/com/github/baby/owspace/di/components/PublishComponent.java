package com.github.baby.owspace.di.components;

import com.github.baby.owspace.di.modules.PublishModule;
import com.github.baby.owspace.di.scopes.UserScope;
import com.github.baby.owspace.view.activity.PublishDiscussActivity;

import dagger.Component;

@UserScope
@Component(modules = PublishModule.class,dependencies = NetComponent.class)
public interface PublishComponent {
    void inject(PublishDiscussActivity activity);
}
