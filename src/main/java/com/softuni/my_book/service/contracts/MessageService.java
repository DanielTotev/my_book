package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.MessageServiceModel;

import java.util.List;

public interface MessageService {

    List<MessageServiceModel> getAllByChatId(String chatId);
}
