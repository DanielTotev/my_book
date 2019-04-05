package com.softuni.my_book.domain.models.service;

public class PostServiceModel {
    private String id;
    private String title;
    private String imageUrl;
    private UserServiceModel uploader;

    public PostServiceModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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
}
