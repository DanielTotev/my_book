package com.softuni.my_book.domain.models.binding;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class MessageBindingModel {
    private String text;

    @NotNull
    @NotEmpty
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
