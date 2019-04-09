package com.softuni.my_book.errors;


import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = ErrorMessages.ILLEGAL_USER_DATA_MESSAGE)
public class IllegalUserDataException extends BaseCustomException {

    public IllegalUserDataException() {
        super(HttpStatus.BAD_REQUEST.value());
    }

}
