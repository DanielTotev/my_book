package com.softuni.my_book.web.controllers;

import com.softuni.my_book.domain.models.binding.PostCreateBindingModel;
import com.softuni.my_book.service.contracts.PostService;
import com.softuni.my_book.web.controllers.base.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PostController extends BaseController {
    private final PostService postService;
    private final ModelMapper mapper;


    @Autowired
    public PostController(PostService postService, ModelMapper mapper) {
        this.postService = postService;
        this.mapper = mapper;
    }

    @GetMapping("/post/create")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createPost(@ModelAttribute("bindingModel") PostCreateBindingModel postCreateBindingModel) {
        return super.view("post-create");
    }
}
