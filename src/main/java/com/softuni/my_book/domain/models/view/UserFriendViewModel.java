package com.softuni.my_book.domain.models.view;

public class UserFriendViewModel {
    private String username;
    private String profilePicture;

    public UserFriendViewModel() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }
}
