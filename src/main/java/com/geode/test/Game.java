package com.geode.test;

public class Game {
    private String title;
    private String message;
    private Player player;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "Game{" +
                "title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", player=" + player +
                '}';
    }
}
