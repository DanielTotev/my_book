package com.softuni.my_book.domain.models.view;

import com.google.gson.annotations.Expose;

public class MessageViewModel {

    @Expose
    private String text;

    @Expose
    private String userUsername;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }
}
