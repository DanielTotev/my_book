package com.softuni.my_book.unit.util;

import com.google.gson.GsonBuilder;
import com.softuni.my_book.domain.models.view.MessageViewModel;
import com.softuni.my_book.util.JsonParserImpl;
import com.softuni.my_book.util.contracts.JsonParser;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class JsonParserTests {
    private JsonParser jsonParser;

    @Before
    public void setUp() {
        this.jsonParser = new JsonParserImpl(new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create());
    }

    @Test
    public void parseToJson_withDataObject_expectCorrectResult() {
        MessageViewModel messageViewModel = new MessageViewModel();
        messageViewModel.setSenderName("Daniel");
        messageViewModel.setText("Hi!");

        String parsed = this.jsonParser.parseToJson(messageViewModel);
        String expected="{\"text\":\"Hi!\",\"senderName\":\"Daniel\"}";
        Assert.assertEquals(expected, parsed);
    }

    @Test
    public void parseToJson_withEmptyObject_expectCorrectResult() {
        MessageViewModel messageViewModel = new MessageViewModel();
        String expected = "{}";
        String actual = this.jsonParser.parseToJson(messageViewModel);
        Assert.assertEquals(expected, actual);
    }
}
