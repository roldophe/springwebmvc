package com.example.springwebmvc.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BaseError {

    private boolean status;
    private int code;
    private String message;
    private Object errors;
    private LocalDateTime timestamp;
}