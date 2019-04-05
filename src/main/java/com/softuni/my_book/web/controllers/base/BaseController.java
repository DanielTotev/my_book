package com.softuni.my_book.web.controllers.base;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    protected ModelAndView view(String viewName, ModelAndView modelAndView) {
        modelAndView.setViewName(viewName);
        return modelAndView;
    }

    protected ModelAndView view(String viewName) {
        return this.view(viewName, new ModelAndView());
    }

    protected ModelAndView redirect(String url) {
        return this.view("redirect:" + url);
    }

    protected String getCurrentUrl(HttpServletRequest request) {
        return request.getRequestURL() + "?" + request.getQueryString();
    }
}
