package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.binding.ProfileCreateBindingModel;
import com.softuni.my_book.domain.models.service.ProfileServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.domain.models.view.ProfileViewModel;
import com.softuni.my_book.errors.profile.ProfileNotFoundException;
import com.softuni.my_book.service.contracts.CloudinaryService;
import com.softuni.my_book.service.contracts.ProfileService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;

@Controller
public class ProfileController extends BaseController {
    private final ProfileService profileService;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;
    private final ModelMapper mapper;

    @Autowired
    public ProfileController(ProfileService profileService, CloudinaryService cloudinaryService, UserService userService, ModelMapper mapper) {
        this.profileService = profileService;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
        this.mapper = mapper;
    }


    @GetMapping("/profile/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView create(Principal principal) {
        try {
            this.profileService.getByUsername(principal.getName());
            return super.redirect("/profile/me");
        } catch (ProfileNotFoundException ex) {
            return super.view("profile-create");
        }
    }

    @PostMapping("/profile/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createConfirm(@ModelAttribute ProfileCreateBindingModel profileCreateBindingModel, Principal principal) throws IOException {
        String uploadedImageUrl = this.cloudinaryService.uploadImage(profileCreateBindingModel.getProfilePicture());
        if(uploadedImageUrl == null) {
            throw new IllegalArgumentException("Picture upload failed");
        }

        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName());

        ProfileServiceModel profileServiceModel = this.mapper.map(profileCreateBindingModel, ProfileServiceModel.class);
        profileServiceModel.setProfilePicture(uploadedImageUrl);
        profileServiceModel.setUser(userServiceModel);

        ProfileServiceModel savedProfile = this.profileService.create(profileServiceModel);
        return super.redirect("/profile/me");
    }

    @GetMapping("/profile/me")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profilePage(ModelAndView modelAndView, Principal principal) {
        ProfileServiceModel profileServiceModel = this.profileService.getByUsername(principal.getName());
        ProfileViewModel viewModel = this.mapper.map(profileServiceModel, ProfileViewModel.class);
        viewModel.setUsername(principal.getName());

        modelAndView.addObject("profile", viewModel);

        return super.view("profile-details", modelAndView);
    }

    @GetMapping("/users/profile/{username}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView profileDetails(@PathVariable("username") String username, ModelAndView modelAndView) {
        ProfileServiceModel profileServiceModel = this.profileService
                .getByUsername(username);

        ProfileViewModel profileViewModel = this.mapper.map(profileServiceModel, ProfileViewModel.class);
        profileViewModel.setUsername(username);

        modelAndView.addObject("profile", profileViewModel);
        return super.view("profile-details", modelAndView);
    }
}