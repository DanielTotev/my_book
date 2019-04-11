package com.softuni.my_book.service;

import com.ea.async.Async;
import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.post.IllegalPostDataException;
import com.softuni.my_book.errors.post.PostAlreadyLikedException;
import com.softuni.my_book.errors.post.PostNotFoundException;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    @Override
    public boolean likePost(String postId, String username) {
        UserServiceModel userServiceModel = this.userService.findByUsername(username);
        PostServiceModel postServiceModel = findById(postId);
        postServiceModel.getUsersLikedPost().add(userServiceModel);

        if(postServiceModel.getUsersLikedPost().stream().anyMatch(x -> x.getUsername().equals(username))) {
            throw new PostAlreadyLikedException();
        }

        Post post = this.mapper.map(postServiceModel, Post.class);
        this.postRepository.saveAndFlush(post);
        return true;
    }

    @Override
    public PostServiceModel findById(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return this.mapper.map(post, PostServiceModel.class);
    }

    private CompletableFuture<List<PostServiceModel>> getUserPosts(String username) {
        List<PostServiceModel> posts = postRepository.getAllByUploaderUsername(username)
                .stream()
                .map(p -> this.mapper.map(p, PostServiceModel.class))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(posts);
    }

    private CompletableFuture<List<PostServiceModel>>getFriendsPostsByUsername(String username) {
        List<PostServiceModel> posts = this.postRepository.getFriendsPosts(username)
                .stream()
                .map(x -> this.mapper.map(x, PostServiceModel.class))
                .collect(Collectors.toList());

        return CompletableFuture.completedFuture(posts);
    }
}
