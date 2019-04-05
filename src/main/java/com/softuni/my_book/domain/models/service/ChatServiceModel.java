package com.softuni.my_book.domain.models.service;

import java.util.List;

public class ChatServiceModel {
    private String id;
    private UserServiceModel firstUser;
    private UserServiceModel secondUser;
    private List<MessageServiceModel> messages;

    public ChatServiceModel() {
    }

    public UserServiceModel getFirstUser() {
        return firstUser;
    }

    public void setFirstUser(UserServiceModel firstUser) {
        this.firstUser = firstUser;
    }

    public UserServiceModel getSecondUser() {
        return secondUser;
    }

    public void setSecondUser(UserServiceModel secondUser) {
        this.secondUser = secondUser;
    }

    public List<MessageServiceModel> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageServiceModel> messages) {
        this.messages = messages;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
