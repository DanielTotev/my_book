package com.softuni.my_book.service;

import com.softuni.my_book.domain.entities.Chat;
import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.service.ChatServiceModel;
import com.softuni.my_book.repository.ChatRepository;
import com.softuni.my_book.service.contracts.ChatService;
import com.softuni.my_book.service.contracts.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public ChatServiceImpl(ChatRepository chatRepository, UserService userService, ModelMapper mapper) {
        this.chatRepository = chatRepository;
        this.userService = userService;
        this.mapper = mapper;
    }

//    @Override
//    public ChatServiceModel saveChat(ChatServiceModel chatServiceModel) {
//        try {
//            Chat chat = this.mapper.map(chatServiceModel, Chat.class);
//            return this.mapper.map(this.chatRepository.saveAndFlush(chat), ChatServiceModel.class);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    @Override
    public ChatServiceModel saveChat(String firstUserUsername, String secondUserUsername) {
        Chat chat = new Chat();
        User firstUser = this.mapper.map(this.userService.findByUsername(firstUserUsername), User.class);
        User secondUser = this.mapper.map(this.userService.findByUsername(secondUserUsername), User.class);

        if(firstUser == null || secondUser == null) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        chat.setFirstUser(firstUser);
        chat.setSecondUser(secondUser);

        return this.mapper.map(this.chatRepository.saveAndFlush(chat), ChatServiceModel.class);
    }

    @Override
    public ChatServiceModel findByUsernames(String[] usernames) {
        Chat chat = this.chatRepository.getChatByUsernames(usernames).orElse(null);

        if(chat == null) {
            return null;
        }

        return this.mapper.map(chat, ChatServiceModel.class);
    }

    @Override
    public ChatServiceModel findById(String id) {
        Chat chat = this.chatRepository.findById(id).orElse(null);

        if(chat == null) {
            throw new IllegalArgumentException("Something went wrong");
        }

        return this.mapper.map(chat, ChatServiceModel.class);
    }
}
