package com.github.baby.owspace.di.components;

import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.di.modules.DiscussModule;
import com.github.baby.owspace.di.scopes.UserScope;
import com.github.baby.owspace.view.activity.DiscussContentActivity;
import com.github.baby.owspace.view.fragment.DiscussNewFragment;
import com.github.baby.owspace.view.fragment.DiscussNewsFragment;
import com.github.baby.owspace.view.fragment.DiscussQAFragment;

import dagger.Component;

@UserScope
@Component(modules = DiscussModule.class,dependencies = NetComponent.class)
public interface DiscussComponent {
    void inject(DiscussNewFragment discussNewFragment);
    void inject(DiscussNewsFragment discussNewsFragment);
    void inject(DiscussQAFragment discussQAFragment);
    void inject(DiscussContentActivity discussContentActivity);
}
