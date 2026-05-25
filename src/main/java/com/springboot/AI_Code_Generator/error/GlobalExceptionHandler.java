package com.springboot.AI_Code_Generator.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ApiError> badRequestExceptionHandler(BadRequestException e){
        ApiError apiError = new ApiError(e.getMessage(), HttpStatus.BAD_REQUEST);
        log.error(apiError.toString(), e);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> resourceNotFoundExceptionHandler(ResourceNotFoundException e){
        ApiError apiError = new ApiError(
                "Resource "+e.getResourceName()+" with resourceId "+e.getResourceId()+" not found"
                ,HttpStatus.NOT_FOUND);
        log.error(apiError.toString(), e);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> invalidInputExceptionHandler(MethodArgumentNotValidException e) {

        List<ApiFieldError> fieldErrorList = e.getBindingResult().getFieldErrors().stream()
                .map((f)->new ApiFieldError(f.getField(), f.getDefaultMessage()))
                .toList();

        ApiError apiError = new ApiError(
                "Invalid input",
                HttpStatus.BAD_REQUEST,
                fieldErrorList);

        log.error(apiError.toString(), e);
        return ResponseEntity.status(apiError.status()).body(apiError);
    }
}
