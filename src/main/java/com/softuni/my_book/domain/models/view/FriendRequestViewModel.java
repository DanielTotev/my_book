package com.softuni.my_book.domain.models.view;

public class FriendRequestViewModel {
    private String id;
    private String senderUsername;
    private String senderProfilePicture;

    public FriendRequestViewModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderUsername() {
        return senderUsername;
    }

    public void setSenderUsername(String senderUsername) {
        this.senderUsername = senderUsername;
    }

    public String getSenderProfilePicture() {
        return senderProfilePicture;
    }

    public void setSenderProfilePicture(String senderProfilePicture) {
        this.senderProfilePicture = senderProfilePicture;
    }
}
