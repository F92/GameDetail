package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.entity.ArticalList;
import com.github.baby.owspace.model.entity.DiscussContentList;
import com.github.baby.owspace.model.entity.DiscussList;
import com.github.baby.owspace.model.entity.DiscussNewList;
import com.github.baby.owspace.model.entity.HomeList;
import com.github.baby.owspace.model.entity.Item;

import java.util.List;

public interface DiscussContract {
    interface Presenter{
        void getArtical(String gameName);
        void getDiscuss(String gameName);
        void getQA(String gameName);
        void getComment(int discussId);
        void reply(String userName, String replyName,int discussId,int rcommentId,String comment);
    }
    interface View{
        void showLoading();
        void dismissLoading();
        void showNoData();
        void showNoMore();
        void updateListQA(List<DiscussNewList> itemList);
        void updateListNews(List<ArticalList> itemList);
        void updateListDiscuss(List<DiscussNewList> itemList);
        void updateListContent(List<DiscussContentList> itemList);
        void showOnFailure();

    }
}
