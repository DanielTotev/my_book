package com.softuni.my_book.service.contracts;

import com.softuni.my_book.domain.models.service.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserServiceModel userServiceModel);

    UserServiceModel findById(String id);

    UserServiceModel merge(UserServiceModel userService);

    UserServiceModel findByUsername(String username);

    List<UserServiceModel> findAll();

    boolean makeFriends(String firstUserId, String secondUserId);

    List<UserServiceModel> findAllWithDifferentUsername(String username);

    UserServiceModel setRole(String id, String role);
}
