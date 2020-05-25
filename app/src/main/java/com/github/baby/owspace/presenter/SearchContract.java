package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;

import java.util.List;

public interface SearchContract {
    interface Presenter{
        void getGameList();
        void getDiscussList();
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI(List<DiscussList> itemList);
        void updateListUI2(List<HomeList> itemList);
        void showOnFailure();
    }
}
