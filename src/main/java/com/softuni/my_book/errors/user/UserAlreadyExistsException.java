package com.softuni.my_book.errors.user;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = ErrorMessages.USER_ALREADY_EXISTS_MESSAGE)
public class UserAlreadyExistsException extends BaseCustomException {
    public UserAlreadyExistsException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
}
