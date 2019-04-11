package com.softuni.my_book.errors.post;


import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessages.POST_NOT_FOUND_MESSAGE)
public class PostNotFoundException extends BaseCustomException {

    public PostNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}
