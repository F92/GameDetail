package com.github.baby.owspace.model.entity;

public class DiscussNewList {
    private int discussId;
    private String discussTitle;
    private String discussDetail;
    private String userImage;
    private String userName;

    public int getDiscussId() {
        return discussId;
    }

    public void setDiscussId(int discussId) {
        this.discussId = discussId;
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

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
