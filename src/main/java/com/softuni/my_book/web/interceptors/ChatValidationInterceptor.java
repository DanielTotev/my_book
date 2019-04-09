package com.softuni.my_book.web.interceptors;

import com.softuni.my_book.domain.models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ChatValidationInterceptor extends HandlerInterceptorAdapter {
    private final ModelMapper mapper;

    @Autowired
    public ChatValidationInterceptor(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String friendUsername = request.getQueryString().split("=")[1];
        UserServiceModel user = this.mapper.map(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(), UserServiceModel.class);

        if(user.getFriends().stream().noneMatch(u -> u.getUsername().equals(friendUsername))) {
            response.sendRedirect("/friends");
            return false;
        }

        return true;
    }
}
