package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.domain.models.view.FriendRequestViewModel;
import com.softuni.my_book.service.contracts.FriendRequestService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class FriendRequestController extends BaseController {
    private final FriendRequestService friendRequestService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService, UserService userService, ModelMapper mapper) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @PostMapping("/addFriend")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addFriendConfirm(@RequestParam("friend_username") String friendUsername, Principal principal) {
        UserServiceModel currentUser = this.userService.findByUsername(principal.getName());
        UserServiceModel friendToAdd = this.userService.findByUsername(friendUsername);

        if(currentUser == null || friendToAdd == null) {
            throw new IllegalArgumentException("Something went wrong");
        }

        boolean isSend = this.friendRequestService.sendFriendRequest(currentUser, friendToAdd);

        if(!isSend) {
            throw new IllegalArgumentException("Something went wrong");
        }

        return super.redirect("/friends/discover");
    }

    @GetMapping("/friends/request")
    public ModelAndView friendRequests(Principal principal, ModelAndView modelAndView) {
        List<FriendRequestViewModel> friendRequests =
                this.friendRequestService.getFriendRequestsForUser(principal.getName())
                .stream()
                .map(friendRequest -> {
                    FriendRequestViewModel viewModel = this.mapper.map(friendRequest, FriendRequestViewModel.class);
                    viewModel.setSenderProfilePicture(friendRequest.getUser().getProfile().getProfilePicture());
                    viewModel.setSenderUsername(friendRequest.getUser().getUsername());

                    return viewModel;
                })
                .collect(Collectors.toList());

        modelAndView.addObject("friendRequests", friendRequests);

        return super.view("friend-requests", modelAndView);
    }

    @PostMapping("/friendRequest/accept")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView acceptFriendRequest(@RequestParam("id") String requestId) {
        boolean isSuccessful = this.friendRequestService.acceptFriendRequest(requestId);

        if(!isSuccessful) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        return super.redirect("/friends/request");
    }

    @PostMapping("/friendRequest/decline")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView declineFriendRequest(@RequestParam("id") String id) {
        boolean isSuccessful = this.friendRequestService.declineFriendRequest(id);

        if(!isSuccessful) {
            throw new IllegalArgumentException("Something went wrong!");
        }

        return super.redirect("/friends/request");
    }
}