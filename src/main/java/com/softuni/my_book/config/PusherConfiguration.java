package com.softuni.my_book.config;

import com.pusher.rest.Pusher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PusherConfiguration {
    @Value("${pusher.app-id}")
    private String appId;

    @Value("${pusher.key}")
    private String key;

    @Value("${pusher.secret}")
    private String secret;

    @Bean
    public Pusher pusher() {
        return new Pusher(this.appId, this.key, this.secret);
    }
}