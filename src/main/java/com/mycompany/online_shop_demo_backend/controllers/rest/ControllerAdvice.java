package com.mycompany.online_shop_demo_backend.controllers.rest;

import com.mycompany.online_shop_demo_backend.exceptions.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(EntityNotFoundException.class)
    ApiError entityNotFoundException(EntityNotFoundException ex) {
        return new ApiError(HttpStatus.NOT_FOUND, ex.getMessage());
    }
}
