package com.softuni.my_book.service.contracts;

public interface EmailService {
    void sendSimpleMessage(String to, String subject, String text);
}