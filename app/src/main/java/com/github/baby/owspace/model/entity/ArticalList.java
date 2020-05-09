package com.github.baby.owspace.model.entity;

import java.io.Serializable;

public class ArticalList implements Serializable {
    private int articalId;
    private String articalName;
    private String articalIntroduce;
    private String articalImage;
    private String userName;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getArticalId() {
        return articalId;
    }

    public void setArticalId(int articalId) {
        this.articalId = articalId;
    }

    public String getArticalName() {
        return articalName;
    }

    public void setArticalName(String articalName) {
        this.articalName = articalName;
    }

    public String getArticalIntroduce() {
        return articalIntroduce;
    }

    public void setArticalIntroduce(String articalIntroduce) {
        this.articalIntroduce = articalIntroduce;
    }

    public String getArticalImage() {
        return articalImage;
    }

    public void setArticalImage(String articalImage) {
        this.articalImage = articalImage;
    }
}
