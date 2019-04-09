package com.softuni.my_book.domain.models.service;

import java.time.LocalDate;

public class FriendRequestServiceModel {
    private String id;
    private UserServiceModel user;
    private UserServiceModel requestedFriend;
    private LocalDate sendAt;

    public FriendRequestServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserServiceModel getUser() {
        return user;
    }

    public void setUser(UserServiceModel user) {
        this.user = user;
    }

    public UserServiceModel getRequestedFriend() {
        return requestedFriend;
    }

    public void setRequestedFriend(UserServiceModel requestedFriend) {
        this.requestedFriend = requestedFriend;
    }

    public LocalDate getSendAt() {
        return sendAt;
    }

    public void setSendAt(LocalDate sendAt) {
        this.sendAt = sendAt;
    }
}
