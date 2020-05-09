package com.github.baby.owspace.model.entity;

import java.io.Serializable;

public class DiscussList implements Serializable {
    int discussId;
    String userImage;
    String userName;
    String gameName;
    String discussTitle;
    String discussDetail;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDiscussTitle() {
        return discussTitle;
    }

    public void setDiscussTitle(String discussTitle) {
        this.discussTitle = discussTitle;
    }

    public String getDiscussDetail() {
        return discussDetail;
    }

    public void setDiscussDetail(String discussDetail) {
        this.discussDetail = discussDetail;
    }
}
