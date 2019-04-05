package com.softuni.my_book.service;

import com.softuni.my_book.domain.models.service.MessageServiceModel;
import com.softuni.my_book.repository.MessageRepository;
import com.softuni.my_book.service.contracts.MessageService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final ModelMapper mapper;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, ModelMapper mapper) {
        this.messageRepository = messageRepository;
        this.mapper = mapper;
    }


    @Override
    public List<MessageServiceModel> getAllByChatId(String chatId) {
        return this.messageRepository.getAllByChatId(chatId)
                .stream()
                .map(chat -> this.mapper.map(chat, MessageServiceModel.class))
                .collect(Collectors.toList());
    }
}
