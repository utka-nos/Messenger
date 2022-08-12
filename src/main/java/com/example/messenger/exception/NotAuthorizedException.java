package com.example.messenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class NotAuthorizedException extends MyRuntimeException {
    public NotAuthorizedException(String s) {
        super(s);
    }
}
