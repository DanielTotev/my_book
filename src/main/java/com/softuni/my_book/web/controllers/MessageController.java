package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.binding.MessageBindingModel;
import com.softuni.my_book.domain.models.service.MessageServiceModel;
import com.softuni.my_book.domain.models.view.MessageViewModel;
import com.softuni.my_book.service.contracts.MessageService;
import com.softuni.my_book.service.contracts.PusherService;
import com.softuni.my_book.util.contracts.JsonParser;
import com.softuni.my_book.util.contracts.ValidationUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
                .map(m -> this.mapper.map(m, MessageViewModel.class))
                .collect(Collectors.toList());
        return this.jsonParser.parseToJson(messages);
    }

    @PostMapping(value = "/add", consumes = "application/json")
    @ResponseBody
    public String addMessage(@RequestBody MessageBindingModel messageBindingModel) {
        MessageServiceModel messageServiceModel = this.messageService.saveMessage(messageBindingModel.getText(), messageBindingModel.getChatId(), messageBindingModel.getSenderName());
        MessageViewModel viewModel =this.mapper.map(messageServiceModel, MessageViewModel.class);

        this.pusherService.triggerEvent("message-channel", "message-event", "message", viewModel);

        return this.jsonParser.parseToJson(viewModel);
    }
}
