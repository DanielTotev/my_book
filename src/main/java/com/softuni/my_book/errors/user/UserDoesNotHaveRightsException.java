package com.softuni.my_book.errors.user;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ErrorMessages.USER_DOES_NOT_HAVE_RIGHTS_MESSAGE)
public class UserDoesNotHaveRightsException extends BaseCustomException {

    public UserDoesNotHaveRightsException() {
        super(HttpStatus.UNAUTHORIZED.value());
    }
}
