package com.softuni.my_book.service;

import com.pusher.rest.Pusher;
import com.softuni.my_book.service.contracts.PusherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PusherServiceImpl implements PusherService {
    private final Pusher pusher;

    @Autowired
    public PusherServiceImpl(Pusher pusher) {
        this.pusher = pusher;
    }

    @Override
    public void triggerEvent(String channelName, String eventName, String dataName, Object data) {
        this.pusher.trigger(channelName, eventName, Collections.singletonMap(dataName, data));
    }
}
