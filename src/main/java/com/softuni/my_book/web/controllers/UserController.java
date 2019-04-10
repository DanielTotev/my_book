package com.softuni.my_book.web.controllers;

import com.softuni.my_book.constants.ErrorMessages;
import com.softuni.my_book.domain.models.binding.UserRegisterBindingModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.domain.models.view.UserAddFriendViewModel;
import com.softuni.my_book.domain.models.view.UserAllViewModel;
import com.softuni.my_book.domain.models.view.UserFriendViewModel;
import com.softuni.my_book.errors.base.BaseCustomException;
import com.softuni.my_book.errors.user.UserPasswordsDoNotMatchException;
import com.softuni.my_book.service.contracts.FriendRequestService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class UserController extends BaseController {
    private final UserService userService;
    private final ModelMapper mapper;
    private final FriendRequestService friendRequestService;

    @Autowired
    public UserController(UserService userService, ModelMapper mapper, FriendRequestService friendRequestService) {
        this.userService = userService;
        this.mapper = mapper;
        this.friendRequestService = friendRequestService;
    }


    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute(name = "bindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        return super.view("register");
    }

    @PostMapping("/register")
    public ModelAndView registerConfirm(@ModelAttribute(name = "bindingModel") UserRegisterBindingModel userRegisterBindingModel, ModelAndView modelAndView) {
        try {
            if(!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
                throw new UserPasswordsDoNotMatchException();
            }
            this.userService.registerUser(this.mapper.map(userRegisterBindingModel, UserServiceModel.class));
        } catch (BaseCustomException ex) {
            String error = ex.getClass().getAnnotation(ResponseStatus.class).reason();
            modelAndView.addObject("error", error);
            return super.view("register", modelAndView);
        }
        return super.redirect("/login");
    }

    @GetMapping("/login")
    public ModelAndView login() {
        return super.view("login");
    }

    @GetMapping("/friends/discover")
    public ModelAndView discoverFriends(Principal principal, ModelAndView modelAndView) {
        // TODO should be refactored
        UserServiceModel currentUser = this.userService.findByUsername(principal.getName());
        List<String> requestedUsersIds = this.friendRequestService.getUserRequestedFriendsIds(currentUser.getId());
        // get the people who send friend request to the current user
        List<String> potentialFriends = this.friendRequestService.getPotentialFriendsIds(currentUser.getId());

        List<UserAddFriendViewModel> people = this.userService.findAll()
                .stream()
                .filter(x -> !x.getId().equals(currentUser.getId()))
                .filter(u -> u.getProfile() != null)
                .filter(u -> !u.getFriends().stream().map(UserServiceModel::getId).collect(Collectors.toList()).contains(currentUser.getId()))
                .filter(u -> !potentialFriends.contains(u.getId()))
                .filter(u -> !requestedUsersIds.contains(u.getId()))
                .map(u -> {
                    UserAddFriendViewModel viewModel = this.mapper.map(u, UserAddFriendViewModel.class);
                    viewModel.setProfilePicture(u.getProfile().getProfilePicture());
                    return viewModel;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("people", people);
        return super.view("friends-discover", modelAndView);
    }

    @GetMapping("/friends")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView getFriends(Principal principal, ModelAndView modelAndView) {
        List<UserFriendViewModel> friends =
                this.userService.findByUsername(principal.getName())
                .getFriends()
                .stream()
                .map(u -> {
                    UserFriendViewModel friend = this.mapper.map(u, UserFriendViewModel.class);
                    friend.setProfilePicture(u.getProfile().getProfilePicture());

                    return friend;
                })
                .collect(Collectors.toList());
        modelAndView.addObject("friends", friends);

        return super.view("friends", modelAndView);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView admin(Principal principal, ModelAndView modelAndView) {
        List<UserAllViewModel> users = this.userService.findAllWithDifferentUsername(principal.getName())
                .stream()
                .map(u -> this.mapper.map(u, UserAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("users", users);

        return super.view("admin", modelAndView);
    }

    @PostMapping("/users/set-moderator/{id}")
    public ModelAndView setModerator(@PathVariable("id") String  id){
        this.userService.setRole(id, "ROLE_MODERATOR");
        return super.redirect("/admin");
    }

    @PostMapping("/users/set-user/{id}")
    public ModelAndView setUser(@PathVariable("id") String  id){
        this.userService.setRole(id, "ROLE_USER");
        return super.redirect("/admin");
    }
}