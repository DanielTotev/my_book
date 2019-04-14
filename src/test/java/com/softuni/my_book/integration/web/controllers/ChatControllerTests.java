package com.softuni.my_book.integration.web.controllers;

import com.softuni.my_book.domain.entities.Chat;
import com.softuni.my_book.domain.entities.Profile;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.enums.Gender;
import com.softuni.my_book.repository.ChatRepository;
import com.softuni.my_book.repository.ProfileRepository;
import com.softuni.my_book.repository.UserRepository;
import com.softuni.my_book.service.contracts.ChatService;
import com.softuni.my_book.web.controllers.ChatController;
import com.softuni.my_book.web.interceptors.ProfileValidationInterceptor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChatControllerTests {
    private static final String CHAT_CREATE_URL = "/chat";
    private static final String REDIRECT_URL = "http://localhost/login";
    private static final String FIRST_USER_USERNAME = "User 1";
    private static final String SECOND_USER_USERNAME = "User 2";

    @Autowired
    private ChatService chatService;

    private ChatController chatController;

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private Principal principal;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ProfileValidationInterceptor profileValidationInterceptor;

    private ProfileRepository profileRepository;

    @Before
    public void init() {
        this.chatController = new ChatController(this.chatService);
        if(this.userRepository.count() == 0) {
            User firstUser = new User();
            firstUser.setUsername(FIRST_USER_USERNAME);
            firstUser.setEmail("test@abv.bg");
            firstUser.setPassword("test password");
            firstUser.setAuthorities(new HashSet<>());
            firstUser.setFriends(new ArrayList<>());

            User secondUser = new User();
            secondUser.setUsername(SECOND_USER_USERNAME);
            secondUser.setEmail("test1@abv.bg");
            secondUser.setPassword("123");
            secondUser.setAuthorities(new HashSet<>());
            secondUser.setFriends(new ArrayList<>());


            firstUser.getFriends().add(secondUser);
            secondUser.getFriends().add(firstUser);

            Profile profile = new Profile();
            profile.setUser(firstUser);
            profile.setAge(20);
            profile.setBirthday(LocalDate.now());
            profile.setProfilePicture("http:/");
            profile.setEducation("Doctor");
            profile.setGender(Gender.Male);

            this.userRepository.saveAndFlush(firstUser);
            this.profileRepository.saveAndFlush(profile);
            this.userRepository.saveAndFlush(secondUser);
        }
    }

    @Test
    @WithAnonymousUser
    public void chatCreate_withAnonymousUser_expectRedirect() throws Exception {
        this.mockMvc.perform(get(CHAT_CREATE_URL))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(REDIRECT_URL));
    }

    @Test
    @WithMockUser
    public void chatCreate_withValidUsers_expectCorrectResult() throws Exception {
        Mockito.when(this.principal.getName())
                .thenReturn(FIRST_USER_USERNAME);

        Mockito.when(this.profileValidationInterceptor.preHandle(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(true);

        this.mockMvc.perform(get(CHAT_CREATE_URL).param("friend", SECOND_USER_USERNAME))
            .andExpect(status().is3xxRedirection());

        Chat chat = this.chatRepository.getChatByUsernames(new String[]{FIRST_USER_USERNAME, SECOND_USER_USERNAME})
                .orElse(null);

        Assert.assertNotNull(chat);
    }
}
