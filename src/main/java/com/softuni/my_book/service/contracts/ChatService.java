package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.ChatServiceModel;

public interface ChatService {

    ChatServiceModel saveChat(String firstUserUsername, String secondUserUsername);

    ChatServiceModel findByUsernames(String[] usernames);

    ChatServiceModel findById(String id);

}
