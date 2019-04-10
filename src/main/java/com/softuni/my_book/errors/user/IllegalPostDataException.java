package com.softuni.my_book.errors.user;

import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "")
public class IllegalPostDataException extends BaseCustomException {
    public IllegalPostDataException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
}
