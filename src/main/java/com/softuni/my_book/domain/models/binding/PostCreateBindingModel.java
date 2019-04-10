package com.softuni.my_book.domain.models.binding;

import org.springframework.web.multipart.MultipartFile;

public class PostCreateBindingModel {
    private String title;
    private MultipartFile image;

    public PostCreateBindingModel() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
