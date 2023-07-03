package com.Subodh.SchoolProject.rest;

import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Subodh.SchoolProject.model.Response;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice(annotations = RestController.class)
@Order(1) // for providing 1st priority order for global exception error logic
public class GlobalExceptionRestController extends ResponseEntityExceptionHandler {

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                              HttpHeaders headers, HttpStatus status, WebRequest request) {
        Response response = new Response(status.toString(),
                ex.getBindingResult().toString());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Response> exceptionHandler(Exception exception){
        Response response = new Response("500",
                exception.getMessage());
        return new ResponseEntity(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}