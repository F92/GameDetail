package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/2
 * owspace
 */
public interface MainContract {
    interface Presenter{
        void GetHomeList();
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<HomeList> homeLists);
        void showOnFailure();
        void showLunar(String content);
    }
}
