package com.softuni.my_book.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageBindingModel {
    private String text;
    private String chatId;
    private String senderName;

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
    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
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
