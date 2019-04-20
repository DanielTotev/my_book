package com.softuni.my_book.service.contracts;

public interface RecaptchaService {
    String verifyRecapture(String userIpAddress,String gRecaptchaResponse);
}
