package com.springboot.AI_Code_Generator.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ApiError(
        HttpStatus status,
        String message,
        Instant timestamp,
        // this field must be used for validation errors only
        @JsonInclude(JsonInclude.Include.NON_NULL) List<ApiFieldError> fieldErrorList
) {
    public ApiError(String message, HttpStatus status){
        this(status, message, Instant.now(), null);
    }
    public ApiError(String message, HttpStatus status, List<ApiFieldError> fieldErrors){
        this(status, message, Instant.now(), fieldErrors);
    }
}

record ApiFieldError(
        String field,
        String message
){}
