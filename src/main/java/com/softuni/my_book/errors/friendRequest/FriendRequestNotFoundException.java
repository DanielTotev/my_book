package com.softuni.my_book.errors.friendRequest;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = ErrorMessages.FRIEND_REQUEST_NOT_FOUND_EXCEPTION)
public class FriendRequestNotFoundException extends BaseCustomException {
    public FriendRequestNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }
}
