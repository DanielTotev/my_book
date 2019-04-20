package com.softuni.my_book.service;

import com.softuni.my_book.service.contracts.RecaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RecaptchaServiceImpl implements RecaptchaService {
    private static final String GOOGLE_RECAPTCHA_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";
    private static final String RECAPTCHA_SUCCESS_MESSAGE = "Success";

    @Value("${google.recaptcha.secret-key}")
    private String recaptchaSecretKey;

    private final RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public RecaptchaServiceImpl(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    @Override
    public String verifyRecapture(String userIpAddress, String gRecaptchaResponse) {
        Map<String, Object> recaptchaRequestBody = new HashMap<>() {{
            put("secret", recaptchaSecretKey);
            put("response", gRecaptchaResponse);
            put("remoteip", userIpAddress);
        }};


        ResponseEntity<Map> recaptchaResponseEntity = this.restTemplateBuilder
                .build()
                .postForEntity(GOOGLE_RECAPTCHA_VERIFY_URL +
                                "?secret={secret}&response={response}&remoteip={remoteip}",
                        recaptchaRequestBody, Map.class, recaptchaRequestBody);

        Map<String, Object> recaptchaResponseBody = recaptchaResponseEntity.getBody();

        if ((boolean) recaptchaResponseBody.get("success")) {
            return RECAPTCHA_SUCCESS_MESSAGE;
        }

        return null;
    }
}
