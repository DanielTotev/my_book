package com.softuni.my_book.errors.base;

public abstract class BaseCustomException extends RuntimeException {

    private int statusCode;

    public BaseCustomException(int statusCode) {
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
