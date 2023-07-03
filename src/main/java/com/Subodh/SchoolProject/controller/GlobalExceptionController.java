package com.Subodh.SchoolProject.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.stereotype.Controller;
@Slf4j
@ControllerAdvice(annotations = Controller.class)
public class GlobalExceptionController {
@ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandler(Exception exception){

    ModelAndView errorPage=new ModelAndView();
    errorPage.setViewName("error");
    errorPage.addObject("errormsg",exception.getMessage());
    return errorPage;
}

}
