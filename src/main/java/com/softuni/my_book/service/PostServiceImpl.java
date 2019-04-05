package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.service.contracts.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper = mapper;
    }


    @Override
    public PostServiceModel savePost(PostServiceModel postServiceModel) {
        try {
            Post post = this.mapper.map(postServiceModel, Post.class);
            return this.mapper.map(this.postRepository.saveAndFlush(post), PostServiceModel.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
