package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.service.ChatServiceModel;
import com.softuni.my_book.service.contracts.ChatService;
import com.softuni.my_book.service.contracts.MessageService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class ChatController extends BaseController {
    private final UserService userService;
    private final ChatService chatService;
    private final MessageService messageService;
    private final ModelMapper mapper;

    @Autowired
    public ChatController(UserService userService, ChatService chatService, MessageService messageService, ModelMapper mapper) {
        this.userService = userService;
        this.chatService = chatService;
        this.messageService = messageService;
        this.mapper = mapper;
    }


    @GetMapping("/chat")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createChat(@RequestParam("friend") String friendName, Principal principal) {
        ChatServiceModel chatServiceModel = this.chatService.findByUsernames(new String[]{friendName, principal.getName()});

        if(chatServiceModel == null) {
            chatServiceModel = this.chatService.saveChat(principal.getName(), friendName);
        }

        return super.redirect("/chat/" + chatServiceModel.getId());
    }

    @GetMapping("/chat/{id}")
    public ModelAndView getChat(@PathVariable String id, ModelAndView modelAndView, Principal principal) {
        modelAndView.addObject("chatId", id);
        modelAndView.addObject("username", principal.getName());

        return super.view("chat", modelAndView);
    }
}