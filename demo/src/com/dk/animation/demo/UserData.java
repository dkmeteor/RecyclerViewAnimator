package com.dk.animation.demo;

public class UserData {
    private int avatarId;
    private String content;

    public UserData(int avatarId, String content) {
        this.avatarId = avatarId;
        this.content = content;
    }

    public int getAvatar() {
        return avatarId;
    }

    public String getContent() {
        return content;
    }
}
