package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.binding.MessageBindingModel;
import com.softuni.my_book.domain.models.service.MessageServiceModel;

import java.util.List;

public interface MessageService {

    List<MessageServiceModel> getAllByChatId(String chatId);

    MessageServiceModel saveMessage(String messageText, String chatId, String messageUserUsername);
}
