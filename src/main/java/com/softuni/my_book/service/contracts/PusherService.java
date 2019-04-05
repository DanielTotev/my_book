package com.softuni.my_book.service.contracts;

public interface PusherService {
    void triggerEvent(String channelName, String eventName, String dataName, Object data);
}
