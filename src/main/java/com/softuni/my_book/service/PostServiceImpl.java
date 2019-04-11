package com.softuni.my_book.service;

import com.ea.async.Async;
import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.user.IllegalPostDataException;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper mapper;
    private final ValidationUtils validationUtils;
    private final UserService userService;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, ModelMapper mapper, ValidationUtils validationUtils, UserService userService) {
        this.postRepository = postRepository;
        this.mapper = mapper;
        this.validationUtils = validationUtils;
        this.userService = userService;
    }


    @Override
    public PostServiceModel savePost(PostServiceModel postServiceModel) {
        if(!this.validationUtils.isValid(postServiceModel)) {
            throw new IllegalPostDataException();
        }
        Post savedPost = this.postRepository.saveAndFlush(this.mapper.map(postServiceModel, Post.class));
        return this.mapper.map(savedPost, PostServiceModel.class);
    }

    @Override
    public List<PostServiceModel> getAllPostsByUsername(String username) {
        List<PostServiceModel> userPosts = Async.await(getUserPosts(username));
        List<PostServiceModel> friendsPosts = Async.await(getFriendsPostsByUsername(username));

        userPosts.addAll(friendsPosts);

        return userPosts;
    }

    private CompletableFuture<List<PostServiceModel>> getUserPosts(String username) {
        List<PostServiceModel> posts = postRepository.getAllByUploaderUsername(username)
                .stream()
                .map(p -> mapper.map(p, PostServiceModel.class))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(posts);
    }

    private CompletableFuture<List<PostServiceModel>>getFriendsPostsByUsername(String username) {
        List<PostServiceModel> posts = postRepository.getFriendsPosts(username)
                .stream()
                .map(x -> mapper.map(x, PostServiceModel.class))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(posts);
    }
}
