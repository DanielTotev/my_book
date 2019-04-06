package com.softuni.my_book.util.contracts;

public interface JsonParser {
    <E> String parseToJson(E object);
}