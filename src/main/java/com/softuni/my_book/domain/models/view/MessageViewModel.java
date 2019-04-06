package com.softuni.my_book.domain.models.view;

import com.google.gson.annotations.Expose;

public class MessageViewModel {

    @Expose
    private String text;

    @Expose
    private String senderName;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }
}
