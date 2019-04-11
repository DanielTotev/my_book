package com.softuni.my_book.domain.models.service;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class PostServiceModel {
    private String id;
    private String title;
    private String imageUrl;
    private UserServiceModel uploader;
    private List<UserServiceModel> usersLikedPost;

    public PostServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @NotEmpty
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @NotNull
    @NotEmpty
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UserServiceModel getUploader() {
        return uploader;
    }

    public void setUploader(UserServiceModel uploader) {
        this.uploader = uploader;
    }

    public List<UserServiceModel> getUsersLikedPost() {
        return usersLikedPost;
    }

    public void setUsersLikedPost(List<UserServiceModel> usersLikedPost) {
        this.usersLikedPost = usersLikedPost;
    }
}
