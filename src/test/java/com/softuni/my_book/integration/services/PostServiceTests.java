package com.softuni.my_book.integration.services;

import com.softuni.my_book.domain.entities.Post;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.repository.PostRepository;
import com.softuni.my_book.repository.UserRepository;
import com.softuni.my_book.service.PostServiceImpl;
import com.softuni.my_book.service.contracts.CloudinaryService;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

//@SpringBootTest
//@RunWith(SpringRunner.class)
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PostServiceTests {
//
//    @Autowired
//    private PostRepository postRepository;
//
//    @Autowired
//    private ModelMapper mapper;
//
//    @Autowired
//    private ValidationUtils validationUtils;
//
//    @MockBean
//    private UserService userService;
//
//    private PostService postService;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Before
//    public void beforeTest() {
//        this.postService = new PostServiceImpl(postRepository, mapper, validationUtils, userService);
//    }
//
//    @Test
//    public void test_savePost_withCorrectData_expectCorrectOutput() {
//        //Arrange
//        User user = new User();
//        user.setUsername("Test user 1");
//        user.setUsername("Test user 1");
//        this.userRepository.saveAndFlush(user);
//
//        PostServiceModel postServiceModel = new PostServiceModel();
//    }
}
