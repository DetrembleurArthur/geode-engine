package com.geode.test;

import java.io.Serializable;

public class MyGameConfig implements Serializable {
    private String title = "untitled";
    private int playerNumber = 2;

    public MyGameConfig() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }
}
