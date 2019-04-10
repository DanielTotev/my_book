package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.errors.user.IllegalPostDataException;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    private final ValidationUtils validationUtils;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, ValidationUtils validationUtils) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
    }


    @Override
    public PostServiceModel savePost(PostServiceModel postServiceModel) {
        if(!this.validationUtils.isValid(postServiceModel)) {
            throw new IllegalPostDataException();
        }
        Post savedPost = this.postRepository.saveAndFlush(this.mapper.map(postServiceModel, Post.class));
        return this.mapper.map(savedPost, PostServiceModel.class);
    }
}
