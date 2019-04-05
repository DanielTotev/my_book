package com.softuni.my_book.web.interceptors;

import com.softuni.my_book.domain.entities.User;
import com.softuni.my_book.domain.models.securityContext.UserSecurityContextModel;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.service.contracts.ProfileService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class ProfileValidationInterceptor extends HandlerInterceptorAdapter {
    private final ProfileService profileService;
    private final ModelMapper mapper;

    @Autowired
    public ProfileValidationInterceptor(ProfileService profileService, ModelMapper mapper) {
        this.profileService = profileService;
        this.mapper = mapper;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        UserSecurityContextModel user = this.mapper.map(SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal(), UserSecurityContextModel.class);

        ProfileServiceModel profile = this.profileService.getByUsername(user.getUsername());

        if(profile == null) {
            response.sendRedirect("/profile/create");
            return false;
        }

        return true;
    }
}