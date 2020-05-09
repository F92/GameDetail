package com.github.baby.owspace.di.components;

import com.github.baby.owspace.di.modules.ArtModule;
import com.github.baby.owspace.di.scopes.UserScope;
import com.github.baby.owspace.view.activity.ArtActivity;
import com.github.baby.owspace.view.activity.DiscussContentActivity;
import com.github.baby.owspace.view.fragment.ArtFragment;
import com.github.baby.owspace.view.fragment.DiscussFragment;
import com.github.baby.owspace.view.fragment.GameFragment;

import dagger.Component;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/10/22
 * owspace
 */
@UserScope
@Component(modules = ArtModule.class,dependencies = NetComponent.class)
public interface ArtComponent {
    void inject(ArtFragment artFragment);
    void inject(ArtActivity artActivity);
    void inject(DiscussFragment discussFragment);
    void inject(GameFragment gameFragment);

}
