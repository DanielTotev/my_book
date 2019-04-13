package com.softuni.my_book.errors.profile;

import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "")
public class InvalidProfileDataException extends BaseCustomException {
    public InvalidProfileDataException() {
        super(HttpStatus.BAD_REQUEST.value());
    }
}
