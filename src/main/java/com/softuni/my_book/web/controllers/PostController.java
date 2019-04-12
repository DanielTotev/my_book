package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.entities.Role;
import com.softuni.my_book.domain.models.binding.PostCreateBindingModel;
import com.softuni.my_book.domain.models.binding.PostEditBindingModel;
import com.softuni.my_book.domain.models.service.PostServiceModel;
import com.softuni.my_book.domain.models.service.UserServiceModel;
import com.softuni.my_book.domain.models.view.PostViewModel;
import com.softuni.my_book.errors.base.BaseCustomException;
import com.softuni.my_book.service.contracts.CloudinaryService;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.service.contracts.UserService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PostController extends BaseController {
    private final PostService postService;
    private final ModelMapper mapper;
    private final CloudinaryService cloudinaryService;
    private final UserService userService;


    @Autowired
    public PostController(PostService postService, ModelMapper mapper, CloudinaryService cloudinaryService, UserService userService) {
        this.postService = postService;
        this.mapper = mapper;
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }

    @GetMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createPost(@ModelAttribute("bindingModel") PostCreateBindingModel postCreateBindingModel) {
        return view("post-create");
    }

    @PostMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createPostConfirm(@ModelAttribute("bindingModel") PostCreateBindingModel postCreateBindingModel, ModelAndView modelAndView, Principal principal) throws IOException {
        PostServiceModel postServiceModel = this.mapper.map(postCreateBindingModel, PostServiceModel.class);
        String imageUrl = this.cloudinaryService.uploadImage(postCreateBindingModel.getImage());
        postServiceModel.setImageUrl(imageUrl);
        UserServiceModel userServiceModel = this.userService.findByUsername(principal.getName());
        postServiceModel.setUploader(userServiceModel);

        try {
            this.postService.savePost(postServiceModel);
        } catch (BaseCustomException ex) {
            String message = ex.getClass().getAnnotation(ResponseStatus.class).reason();
            modelAndView.addObject("error", message);
            return view("post-create", modelAndView);
        }

        return redirect("/dashboard");
    }

    @GetMapping("/dashboard")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView dashboard(ModelAndView modelAndView, Principal principal) {
        List<String> currentUserRoles = this.userService.findByUsername(principal.getName())
                .getAuthorities()
                .stream()
                .map(Role::getAuthority)
                .collect(Collectors.toList());

        List<PostViewModel> posts =
                this.postService.getAllPostsByUsername(principal.getName())
                        .stream()
                        .map(post -> {
                            PostViewModel postViewModel = this.mapper.map(post, PostViewModel.class);
                            postViewModel.setUsersLikedPost(post.getUsersLikedPost().stream().map(UserServiceModel::getId).collect(Collectors.toList()));
                            postViewModel.setUploaderId(post.getUploader().getId());
                            return postViewModel;
                        })
                        .collect(Collectors.toList());

        modelAndView.addObject("posts", posts);
        modelAndView.addObject("roles", currentUserRoles);
        return view("dashboard", modelAndView);
    }

    @PostMapping("/post/like/{id}")
    public ModelAndView likePost(@PathVariable("id") String id, Principal principal) {
        this.postService.likePost(id, principal.getName());
        return redirect("/dashboard");
    }

    @GetMapping("/post/edit/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editPost(@PathVariable("id") String id, Principal principal, ModelAndView modelAndView, @ModelAttribute("bindingModel") PostEditBindingModel bindingModel) {
        PostServiceModel postServiceModel = this.postService.findById(id);
        UserServiceModel user = this.userService.findByUsername(principal.getName());

        if (!(postServiceModel.getUploader().getUsername().equals(user.getUsername()) ||
                user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_MODERATOR"))
                || user.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN")))) {
            return redirect("/dashboard");
        }
        bindingModel = this.mapper.map(postServiceModel, PostEditBindingModel.class);
        modelAndView.addObject("bindingModel", bindingModel);
        return view("post-edit");
    }


//    public ModelAndView deletePost() {
//
//    }
}