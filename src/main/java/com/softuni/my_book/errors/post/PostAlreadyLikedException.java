package com.softuni.my_book.errors.post;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = ErrorMessages.POST_ALREADY_LIKED_MESSAGE)
public class PostAlreadyLikedException extends BaseCustomException {
    public PostAlreadyLikedException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
}
