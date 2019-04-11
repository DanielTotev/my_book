package com.softuni.my_book;

import com.ea.async.Async;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MyBookApplication {
    public static void main(String[] args) {
        Async.init();
        SpringApplication.run(MyBookApplication.class, args);
    }
}