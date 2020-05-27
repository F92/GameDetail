package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;

import java.util.List;

/**
 * Created by Mr.Yangxiufeng
 * DATE 2016/8/2
 * owspace
 */
public interface ArticalContract {
    interface Presenter{
        void getListByPage(int page, int model, String pageId,String deviceId,String createTime);

        void getDiscussList();
        void getGameList();
        void searchGame(String gameName);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListUI3(List<HomeList> itemList);
        void updateListUI(List<DiscussList> itemList);
        void updateListUI2(List<Item> itemList);
        void showOnFailure();
    }
}
