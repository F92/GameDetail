package com.github.baby.owspace.di.components;

import com.github.baby.owspace.di.modules.DiscussModule;
import com.github.baby.owspace.di.modules.SearchModule;
import com.github.baby.owspace.di.scopes.UserScope;
import com.github.baby.owspace.view.activity.SearchActivity;
import com.github.baby.owspace.view.fragment.DiscussNewFragment;

import dagger.Component;

@UserScope
@Component(modules = SearchModule.class,dependencies = NetComponent.class)
public interface SearchComponent {
    void inject(SearchActivity activity);
}
