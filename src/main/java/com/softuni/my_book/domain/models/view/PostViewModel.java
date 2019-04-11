package com.softuni.my_book.domain.models.view;

import java.util.List;

public class PostViewModel {
    private String id;
    private String title;
    private String imageUrl;
    private List<String> usersLikedPost;


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

    public List<String> getUsersLikedPost() {
        return usersLikedPost;
    }

    public void setUsersLikedPost(List<String> usersLikedPost) {
        this.usersLikedPost = usersLikedPost;
    }
}
