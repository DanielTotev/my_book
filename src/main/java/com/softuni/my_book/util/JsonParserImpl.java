package com.softuni.my_book.util;

import com.google.gson.Gson;
import com.softuni.my_book.util.contracts.JsonParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JsonParserImpl implements JsonParser {
    private final Gson gson;

    @Autowired
    public JsonParserImpl(Gson gson) {
        this.gson = gson;
    }


    @Override
    public <E> String parseToJson(E object) {
        return this.gson.toJson(object);
    }
}
