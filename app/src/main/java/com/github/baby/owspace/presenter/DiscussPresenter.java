package com.github.baby.owspace.presenter;

import com.github.baby.owspace.model.api.ApiService;
import com.github.baby.owspace.model.entity.ArticalList;
import com.github.baby.owspace.model.entity.DiscussContentList;
import com.github.baby.owspace.model.entity.DiscussNewList;
import com.github.baby.owspace.model.entity.HomeList;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DiscussPresenter implements DiscussContract.Presenter {

    private DiscussContract.View view;
    private ApiService apiService;

    @Inject
    public DiscussPresenter(DiscussContract.View view,ApiService apiService) {
        this.view = view;
        this.apiService = apiService;
    }

    @Override
    public void getArtical(String gameName) {
        apiService.getArticalList(gameName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<ArticalList> homeLists = new Gson().fromJson(s, new TypeToken<List<ArticalList>>(){}.getType());
                        view.updateListNews(homeLists);
                    }
                });
    }

    @Override
    public void getDiscuss(String gameName) {
        apiService.getNewDiscussList(gameName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<DiscussNewList> homeLists = new Gson().fromJson(s, new TypeToken<List<DiscussNewList>>(){}.getType());
                        System.out.println(homeLists);
                        view.updateListDiscuss(homeLists);
                    }
                });
    }

    @Override
    public void getQA(String gameName) {
        apiService.getQAList(gameName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<DiscussNewList> homeLists = new Gson().fromJson(s, new TypeToken<List<DiscussNewList>>(){}.getType());
                        System.out.println(homeLists);
                        view.updateListQA(homeLists);
                    }
                });
    }

    @Override
    public void getComment(int discussId) {
        apiService.getCommentList(discussId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<DiscussContentList> homeLists = new Gson().fromJson(s, new TypeToken<List<DiscussContentList>>(){}.getType());
                        System.out.println(homeLists);
                        view.updateListContent(homeLists);
                    }
                });
    }

    @Override
    public void reply(String userName, String replyName,int discussId,int rcommentId,String comment) {
        apiService.Reply(comment,userName,replyName,discussId,rcommentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        List<DiscussContentList> homeLists = new Gson().fromJson(s, new TypeToken<List<DiscussContentList>>(){}.getType());
                        System.out.println(homeLists);
                        view.updateListContent(homeLists);
                    }
                });
    }

}
