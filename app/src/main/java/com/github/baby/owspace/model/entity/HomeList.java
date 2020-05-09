package com.github.baby.owspace.model.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class HomeList implements Serializable {
    int gameId;
    String GameName;
    String gameIntroduce;
    String userName;
    String gameImage;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getGameName() {
        return GameName;
    }

    public void setGameName(String gameName) {
        GameName = gameName;
    }

    public String getGameIntroduce() {
        return gameIntroduce;
    }

    public void setGameIntroduce(String gameIntroduce) {
        this.gameIntroduce = gameIntroduce;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getGameImage() {
        return gameImage;
    }

    public void setGameImage(String gameImage) {
        this.gameImage = gameImage;
    }
}
