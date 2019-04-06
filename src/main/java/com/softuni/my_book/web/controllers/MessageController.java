package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.binding.MessageBindingModel;
import com.softuni.my_book.domain.models.view.MessageViewModel;
import com.softuni.my_book.service.contracts.MessageService;
import com.softuni.my_book.service.contracts.PusherService;
import com.softuni.my_book.util.contracts.JsonParser;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
//@EnableAutoConfiguration
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final ModelMapper mapper;
    private final PusherService pusherService;
    private final JsonParser jsonParser;
    private final ValidationUtils validationUtils;


    @Autowired
    public MessageController(MessageService messageService, ModelMapper mapper, PusherService pusherService, JsonParser jsonParser, ValidationUtils validationUtils) {
        this.messageService = messageService;
        this.mapper = mapper;
        this.pusherService = pusherService;
        this.jsonParser = jsonParser;
        this.validationUtils = validationUtils;
    }

    @GetMapping()
    public String getCurrentChatMessages(@RequestParam("chatId") String chatId) {
        List<MessageViewModel> messages = this.messageService.getAllByChatId(chatId)
                .stream()
                .map(m -> {
                    MessageViewModel messageViewModel = this.mapper.map(m, MessageViewModel.class);
                    messageViewModel.setUserUsername(m.getUser().getUsername());
                    return messageViewModel;
                })
                .collect(Collectors.toList());
        return this.jsonParser.parseToJson(messages);
    }

    @PostMapping()
    public String addMessage(@RequestBody MessageBindingModel messageBindingModel) {
//        this.pusherService.triggerEvent("message-channel", "message-event", "message", "qwe");
        System.out.println(messageBindingModel);
        return this.jsonParser.parseToJson(new MessageViewModel());
    }

//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public ResponseEntity< String > persistPerson(@RequestBody MessageBindingModel messageBindingModel) {
//        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).build();
//    }
}
