package com.softuni.my_book.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageBindingModel {
    private String text;
    private String chatId;
    private String userUsername;

    public MessageBindingModel() {
    }

    @NotNull
    @NotEmpty
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    @NotEmpty
    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @NotNull
    @NotEmpty
    public String getChatId() {
        return chatId;
    }

    public void setChatId(String chatId) {
        this.chatId = chatId;
    }
}
