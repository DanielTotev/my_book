package com.softuni.my_book.integration.web.controllers;

import com.softuni.my_book.service.contracts.FriendRequestService;
import com.softuni.my_book.service.contracts.RecaptchaService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.UserController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTests {
    private static final String REGISTER_URL = "/register";
    private static final String REGISTER_VIEW_NAME = "register";

    private static final String LOGIN_URL = "/login";
    private static final String LOGIN_VIEW_NAME = "login";

    private static final String PARAM_USERNAME = "username";
    private static final String USER_USERNAME = "Test username";
    private static final String INVALID_USERNAME = "";

    private static final String PARAM_PASSWORD = "password";
    private static final String USER_PASSWORD = "Test password";
    private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";

    private static final String PARAM_EMAIL = "email";
    private static final String USER_EMAIL = "test@gmail.com";


    private final static MockHttpServletRequestBuilder POST_USER_VALID_DATA = post(REGISTER_URL)
            .param(PARAM_USERNAME, USER_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);

    private final static MockHttpServletRequestBuilder POST_USER_INVALID_DATA = post(REGISTER_URL)
            .param(PARAM_USERNAME, INVALID_USERNAME)
            .param(PARAM_PASSWORD, USER_PASSWORD)
            .param(PARAM_CONFIRM_PASSWORD, USER_PASSWORD)
            .param(PARAM_EMAIL, USER_EMAIL);


    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private FriendRequestService friendRequestService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RecaptchaService recaptchaService;

    private UserController userController;

    @Before
    public void init() {
        this.userController = new UserController(this.userService, this.mapper, this.friendRequestService, this.recaptchaService);
    }

    @Test
    @WithAnonymousUser
    public void registerGet_withNoUser_expectCorrectResult() throws Exception {
        this.mockMvc.perform(get(REGISTER_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(REGISTER_VIEW_NAME));
    }

    @Test
    @WithMockUser
    public void registerGet_withUser_expectForbidden() throws Exception {
        this.mockMvc.perform(get(REGISTER_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser
    public void registerPost_withUser_expectForbidden() throws Exception {
        this.mockMvc.perform(post(REGISTER_URL))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithAnonymousUser
    public void registerPost_withCorrectData_expectCorrectResult() throws Exception {
        mockMvc.perform(POST_USER_VALID_DATA)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(LOGIN_URL));
    }

    @Test
    @WithAnonymousUser
    public void registerPost_withInCorrectData_expectRegisterPage() throws Exception {
        mockMvc.perform(POST_USER_INVALID_DATA)
                .andExpect(view().name(REGISTER_VIEW_NAME));
    }

    @Test
    @WithAnonymousUser
    public void loginGet_withNoUser_expectCorrectResult() throws Exception {
        this.mockMvc.perform(get(LOGIN_URL))
                .andExpect(status().isOk())
                .andExpect(view().name(LOGIN_VIEW_NAME));
    }

    @Test
    @WithMockUser
    public void loginGet_withUser_expectException() throws Exception {
        this.mockMvc.perform(get(LOGIN_URL))
                .andExpect(status().isForbidden());
    }
}
