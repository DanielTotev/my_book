package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.view.MessageViewModel;
import com.softuni.my_book.service.contracts.MessageService;
import com.softuni.my_book.service.contracts.PusherService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.stream.Collectors;

@Controller
public class MessageController {
    private final MessageService messageService;
    private final ModelMapper mapper;
    private final PusherService pusherService;


    @Autowired
    public MessageController(MessageService messageService, ModelMapper mapper, PusherService pusherService) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.pusherService = pusherService;
    }

    @GetMapping(value = "/messages", produces = "application/json")
    public String getCurrentChatMessages(@RequestParam("chatId") String chatId) {
        return this.messageService.getAllByChatId(chatId)
                .stream()
                .map(m -> this.mapper.map(m, MessageViewModel.class))
                .collect(Collectors.toList()).toString();
    }
}
