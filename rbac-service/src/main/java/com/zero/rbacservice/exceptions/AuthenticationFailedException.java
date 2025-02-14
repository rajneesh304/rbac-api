package com.zero.rbacservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
@Getter
public class AuthenticationFailedException extends RuntimeException{
    private String message;

    public AuthenticationFailedException() {
        super();
    }

    public AuthenticationFailedException(String message) {
        super(message);
        this.message = message;
    }

    public AuthenticationFailedException(String message, Object...args) {
        super(message.formatted(args));
        this.message = message.formatted(args);
    }
}
