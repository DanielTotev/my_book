package com.softuni.my_book.integration.service;

import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.errors.post.IllegalPostDataException;
import com.softuni.my_book.errors.post.PostNotFoundException;
import com.softuni.my_book.errors.user.UserDoesNotHaveRightsException;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.service.PostServiceImpl;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;


@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceTests {

    @MockBean
    private PostRepository postRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ValidationUtils validationUtils;

    @MockBean
    private UserService userService;

    private PostService postService;

    @Before
    public void setUp() {
        this.postService = new PostServiceImpl(this.postRepository, this.mapper, this.validationUtils, this.userService);

        Mockito.when(this.postRepository.saveAndFlush(Mockito.any()))
                .thenAnswer((Answer<Post>) invocation -> {
                    Object[] args = invocation.getArguments();
                   return (Post) args[0];
                });
    }

    @Test
    public void create_withValidData_expectCorrectResult() {
        PostServiceModel postServiceModel = new PostServiceModel();
        postServiceModel.setUploader(new UserServiceModel());
        postServiceModel.setImageUrl("http:/image.com");
        postServiceModel.setTitle("Post title");

        PostServiceModel saved = this.postService.savePost(postServiceModel);

        Assert.assertEquals(postServiceModel.getTitle(), saved.getTitle());
        Assert.assertEquals(postServiceModel.getImageUrl(), saved.getImageUrl());
    }

    @Test(expected = IllegalPostDataException.class)
    public void create_withInvalidData_expectException(){
        this.postService.savePost(new PostServiceModel());
    }

    @Test
    public void getAllPostsByUsername_whenPostsForUserAndFriends_expectCorrectResult() {
        String username = "Test username";
        Mockito.when(this.postRepository.getAllByUploaderUsername(username))
                .thenReturn(List.of(new Post(), new Post()));
        Mockito.when(this.postRepository.getFriendsPosts(username))
                .thenReturn(List.of(new Post()));
        int expectedSize = 3;

        List<PostServiceModel> posts = this.postService.getAllPostsByUsername(username);

        Assert.assertEquals(expectedSize, posts.size());
    }

    @Test
    public void getAllPostsByUsername_whenOnlyUserHasPosts_expectCorrectResult() {
        String username = "Test username";
        Mockito.when(this.postRepository.getAllByUploaderUsername(username))
                .thenReturn(List.of(new Post()));
        Mockito.when(this.postRepository.getFriendsPosts(username))
                .thenReturn(new ArrayList<>());
        int expectedSize = 1;

        List<PostServiceModel> posts = this.postService.getAllPostsByUsername(username);

        Assert.assertEquals(expectedSize, posts.size());
    }

    @Test
    public void getAllPostsByUsername_whenOnlyFriendsHavePosts_expectCorrectResult() {
        String username = "Test username";
        Mockito.when(this.postRepository.getAllByUploaderUsername(username))
                .thenReturn(new ArrayList<>());
        Mockito.when(this.postRepository.getFriendsPosts(username))
                .thenReturn(List.of(new Post(), new Post()));
        int expectedSize = 2;

        List<PostServiceModel> posts = this.postService.getAllPostsByUsername(username);

        Assert.assertEquals(expectedSize, posts.size());
    }

    @Test
    public void getAllPostsByUsername_whenNoOneHasPosts_expectCorrectResult() {
        String username = "Test username";
        Mockito.when(this.postRepository.getAllByUploaderUsername(username))
                .thenReturn(new ArrayList<>());
        Mockito.when(this.postRepository.getFriendsPosts(username))
                .thenReturn(new ArrayList<>());
        int expectedSize = 0;

        List<PostServiceModel> posts = this.postService.getAllPostsByUsername(username);

        Assert.assertEquals(expectedSize, posts.size());
    }

    @Test(expected = IllegalPostDataException.class)
    public void edit_withInvalidInputParams_expectException() {
        this.postService.edit(new PostServiceModel(), "");
    }

    @Test(expected = PostNotFoundException.class)
    public void edit_withNoSuchPostInDb_expectException() {
        String userId = "User test id";
        String postId = "test id";
        PostServiceModel postServiceModel = new PostServiceModel();
        postServiceModel.setId(postId);
        postServiceModel.setTitle("Nakov");
        postServiceModel.setImageUrl("http://image.com");
        postServiceModel.setUploader(new UserServiceModel());

        Mockito.when(this.postRepository.findById(postId))
                .thenReturn(Optional.empty());

        this.postService.edit(postServiceModel,userId);
    }

    @Test(expected = UserDoesNotHaveRightsException.class)
    public void edit_withUserWithOutRights_expectException() {
        String username = "Test username";
        String postId = "test id";
        PostServiceModel postServiceModel = new PostServiceModel();
        postServiceModel.setId(postId);
        postServiceModel.setTitle("Test tile");
        postServiceModel.setImageUrl("http://image.com");
        postServiceModel.setUploader(new UserServiceModel(){{setUsername("Uploader");}});

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(username);
        userServiceModel.setAuthorities(new LinkedHashSet<>());

        Mockito.when(this.postRepository.findById(postId))
                .thenReturn(Optional.of(this.mapper.map(postServiceModel, Post.class)));

        Mockito.when(this.userService.findByUsername(username))
                .thenReturn(userServiceModel);

        this.postService.edit(postServiceModel,username);
    }

    @Test
    public void edit_withCorrectData_expectCorrectResult() {
        String username = "Test username";
        String postId = "test id";
        PostServiceModel postServiceModel = new PostServiceModel();
        postServiceModel.setId(postId);
        postServiceModel.setTitle("Test tile");
        postServiceModel.setImageUrl("http://image.com");
        postServiceModel.setUploader(new UserServiceModel(){{setUsername("Uploader");}});

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(username);
        userServiceModel.setAuthorities(new LinkedHashSet<>());

        User postUploader = new User();
        postUploader.setUsername(username);

        Post postInDb = new Post();
        postInDb.setTitle("In db");
        postInDb.setImageUrl("old url");
        postInDb.setUploader(postUploader);

        Mockito.when(this.postRepository.findById(postId))
                .thenReturn(Optional.of(postInDb));

        Mockito.when(this.userService.findByUsername(username))
                .thenReturn(userServiceModel);

        PostServiceModel edit = this.postService.edit(postServiceModel, username);

        Assert.assertEquals(postServiceModel.getTitle(), edit.getTitle());
        Assert.assertEquals(postServiceModel.getImageUrl(), edit.getImageUrl());
    }
}
