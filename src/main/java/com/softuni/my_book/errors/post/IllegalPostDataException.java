package com.softuni.my_book.errors.post;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = ErrorMessages.ILLEGAL_POST_DATA_MESSAGE)
public class IllegalPostDataException extends BaseCustomException {
    public IllegalPostDataException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
}
