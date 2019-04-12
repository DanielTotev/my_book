package com.softuni.my_book.errors.chat;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessages.CHAT_NOT_FOUND_MESSAGE)
public class ChatNotFoundException extends BaseCustomException {
    public ChatNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}
