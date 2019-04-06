package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Chat;
import com.softuni.my_book.domain.entities.Message;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.ChatServiceModel;
import com.softuni.my_book.domain.models.service.MessageServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.repository.MessageRepository;
import com.softuni.my_book.service.contracts.ChatService;
import com.softuni.my_book.service.contracts.MessageService;
import com.softuni.my_book.service.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ChatService chatService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ChatService chatService, UserService userService, ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.chatService = chatService;
        this.userService = userService;
        this.mapper = mapper;
    }


    @Override
    public List<MessageServiceModel> getAllByChatId(String chatId) {
        return this.messageRepository.getAllByChatId(chatId)
                .stream()
                .map(chat -> this.mapper.map(chat, MessageServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public MessageServiceModel saveMessage(String messageText, String chatId, String messageUserUsername) {
        UserServiceModel user = this.userService.findByUsername(messageUserUsername);
        ChatServiceModel chat = this.chatService.findById(chatId);

        if(user == null || chat == null) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        Message message = new Message();
        message.setText(messageText);
        message.setSenderName(user.getUsername());
        message.setChat(this.mapper.map(chat, Chat.class));
        message.setSendAt(LocalDateTime.now());

        return this.mapper.map(
                this.messageRepository.saveAndFlush(message),
                MessageServiceModel.class
        );
    }
}
