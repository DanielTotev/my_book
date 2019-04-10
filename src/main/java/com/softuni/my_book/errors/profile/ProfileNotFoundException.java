package com.softuni.my_book.errors.profile;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = ErrorMessages.PROFILE_NOT_FOUND_MESSAGE)
public class ProfileNotFoundException extends BaseCustomException {
    public ProfileNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}
