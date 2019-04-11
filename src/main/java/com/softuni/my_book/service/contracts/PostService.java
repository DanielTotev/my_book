package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.PostServiceModel;

import java.util.List;

public interface PostService {
    PostServiceModel savePost(PostServiceModel postServiceModel);

    List<PostServiceModel> getAllPostsByUsername(String username);

    boolean likePost(String postId, String username);

    PostServiceModel findById(String id);
}
