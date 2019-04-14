package com.softuni.my_book.integration.web.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerTests {
    private static final String URL = "/";
    private static final String VIEW_NAME = "index";

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithAnonymousUser
    public void homePage_withAnonymousUser_returnsCorrectViewAndStatus() throws Exception {
        this.mockMvc.perform(get(URL))
                .andExpect(status().isOk())
                .andExpect(view().name(VIEW_NAME));
    }

    @Test
    @WithMockUser
    public void homePage_withAuthenticatedUser_returnsForbidden() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isForbidden());
    }

}
