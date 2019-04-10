package com.softuni.my_book.web.controllers;

import com.softuni.my_book.errors.base.BaseCustomException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;


@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({BaseCustomException.class})
    public ModelAndView handleCustomException(BaseCustomException exception) {
        String message = exception.getClass().getAnnotation(ResponseStatus.class).reason();
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exceptionMessage", message);
        modelAndView.addObject("exceptionStatusCode", exception.getStatusCode());
        return modelAndView;
    }

    @ExceptionHandler({Throwable.class})
    public ModelAndView handleGenericException(Throwable exception) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("exceptionMessage", exception.getMessage());
        return modelAndView;
    }
}
