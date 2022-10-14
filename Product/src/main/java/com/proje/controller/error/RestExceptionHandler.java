package com.proje.controller.error;

import com.proje.model.api.error.ApiError;
import com.proje.model.exceptions.BadRequestException;
import com.proje.model.exceptions.NotFoundException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    protected ResponseEntity<ApiError> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), new Date()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ApiError> handleBadRequestException(BadRequestException ex) {
        return new ResponseEntity<>(new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), new Date()), HttpStatus.BAD_REQUEST);
    }
}