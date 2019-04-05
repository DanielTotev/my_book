package com.softuni.my_book.domain.models.service;

import com.softuni.my_book.domain.entities.Chat;

import java.time.LocalDateTime;

public class MessageServiceModel {
    private UserServiceModel user;
    private ChatServiceModel chat;
    private String text;
    private LocalDateTime sendAt;

    public MessageServiceModel() {
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public ChatServiceModel getChat() {
        return chat;
    }

    public void setChat(ChatServiceModel chat) {
        this.chat = chat;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDateTime sendAt) {
        this.sendAt = sendAt;
    }
}
