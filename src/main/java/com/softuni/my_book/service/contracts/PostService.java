package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.PostServiceModel;

public interface PostService {
    PostServiceModel savePost(PostServiceModel postServiceModel);
}
