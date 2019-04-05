package com.softuni.my_book.config;

import com.softuni.my_book.web.interceptors.ProfileValidationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {
    private final ProfileValidationInterceptor profileValidationInterceptor;

    @Autowired
    public InterceptorConfiguration(ProfileValidationInterceptor profileValidationInterceptor) {
        this.profileValidationInterceptor = profileValidationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.profileValidationInterceptor)
        .excludePathPatterns("/", "/login", "/js/**", "/css/**", "/fonts/**", "/register", "/profile/create");
    }
}
