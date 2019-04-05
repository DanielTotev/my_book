package com.softuni.my_book.domain.entities;

import com.softuni.my_book.domain.entities.base.BaseEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "posts")
public class Post extends BaseEntity {
    private String title;
    private String imageUrl;
    private User uploader;
    private List<User> usersLikedPost;

    public Post() {
    }

    @Column(name = "title", nullable = false)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "image_url", nullable = false)
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "uploader_id", referencedColumnName = "id", nullable = false)
    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    @ManyToMany(targetEntity = User.class)
    @JoinTable(name = "posts_users_likes",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    public List<User> getUsersLikedPost() {
        return usersLikedPost;
    }

    public void setUsersLikedPost(List<User> usersLikedPost) {
        this.usersLikedPost = usersLikedPost;
    }
}
