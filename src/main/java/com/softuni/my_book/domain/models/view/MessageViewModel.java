package com.softuni.my_book.domain.models.view;

public class MessageViewModel {
    private String text;
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
