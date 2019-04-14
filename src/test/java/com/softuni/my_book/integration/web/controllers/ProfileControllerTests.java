package com.softuni.my_book.integration.web.controllers;

import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.enums.Gender;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.service.contracts.CloudinaryService;
import com.softuni.my_book.service.contracts.ProfileService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.ProfileController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProfileControllerTests {
    private static final String PROFILE_CREATE_URL = "/profile/create";
    private static final String PROFILE_CREATE_VIEW_NAME = "profile-create";
    private static final String UNAUTHORIZED_REDIRECT_URL = "http://localhost/login";
    private static final String USER_ALREADY_HAS_PROFILE_REDIRECT_URL = "/profile/me";


    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    private ProfileController profileController;

    private User mockUser;

    @Before
    public void before() {
        this.profileController = new ProfileController(this.profileService,
        this.cloudinaryService, this.userService, this.mapper);
        this.setUpUser();
    }

    private void setUpUser() {
        String username = "Test mockUser";
        String password = "Test password";
        String email = "someone@examplemail.com";
        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(username);
        userServiceModel.setPassword(password);
        userServiceModel.setEmail(email);

        this.mockUser = this.mapper.map(
                this.userService.registerUser(userServiceModel),
                User.class
        );
    }

    private void setUpMockProfile() {
        ProfileServiceModel profileServiceModel = new ProfileServiceModel();
        profileServiceModel.setAge(20);
        profileServiceModel.setGender(Gender.Male);
        profileServiceModel.setEducation("Doctor");
        profileServiceModel.setProfilePicture("http://pp.com");
        profileServiceModel.setBirthday(LocalDate.now());
        profileServiceModel.setUser(this.mapper.map(this.mockUser, UserServiceModel.class));

        this.profileService.create(profileServiceModel);
    }

    @Test
    @WithAnonymousUser
    public void profileCreate_withNoUser_expectRedirect() throws Exception {
        this.mockMvc.perform(get(PROFILE_CREATE_URL))
                .andExpect(redirectedUrl(UNAUTHORIZED_REDIRECT_URL));
    }

    @Test
    public void profileCreateGet_whenUserDoesNotHaveProfile_expectProfileCreatePage() throws Exception {
        this.mockMvc.perform(get(PROFILE_CREATE_URL).with(user(this.mockUser)))
                .andExpect(status().isOk())
                .andExpect(view().name(PROFILE_CREATE_VIEW_NAME));
    }

    @Test
    public void profileCreateGet_whenUserHasProfile_expectRedirect() throws Exception {
        setUpMockProfile();
        this.mockMvc.perform(get(PROFILE_CREATE_URL).with(user(this.mockUser)))
                .andExpect(redirectedUrl(USER_ALREADY_HAS_PROFILE_REDIRECT_URL));
    }

    // Should fix this later
    @Test
    public void profileCreatePost_withValidData_expectRedirect() throws Exception {
        MockHttpServletRequestBuilder request = multipart(PROFILE_CREATE_URL);
        MultipartFile multipartFile = new MockMultipartFile("Test", "test".getBytes());
        request.with(user(this.mockUser));
        request.param("age", String.valueOf(20));
        request.param("birthday", String.valueOf(LocalDate.now()));
        request.param("education", "Doctor");
        ((MockMultipartHttpServletRequestBuilder) request).file((MockMultipartFile) multipartFile);

        this.mockMvc.perform(request)
        .andExpect(redirectedUrl(USER_ALREADY_HAS_PROFILE_REDIRECT_URL));
    }
}
