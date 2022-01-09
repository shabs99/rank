package com.casino.rank.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private HttpStatus status;
    private String timestamp;
    private String errorMessage;

    private ApiError(){
        this.timestamp = LocalDateTime.now().toString();
    }

    public ApiError(HttpStatus status, Throwable e){
        this();
        this.status = status;
        this.errorMessage = e.getLocalizedMessage();
    }
}
