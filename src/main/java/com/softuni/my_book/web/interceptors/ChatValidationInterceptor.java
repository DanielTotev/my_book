package com.softuni.my_book.web.interceptors;

import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.service.contracts.UserService;
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
    private final UserService userService;

    @Autowired
    public ChatValidationInterceptor(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String friendUsername = request.getQueryString().split("=")[1];
        String  username = this.mapper.map(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(), UserServiceModel.class).getUsername();

        UserServiceModel user = this.userService.findByUsername(username);

        if(user.getFriends().stream().noneMatch(u -> u.getUsername().equals(friendUsername))) {
            response.sendRedirect("/friends");
            return false;
        }

        return true;
    }
}
