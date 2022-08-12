package com.example.messenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends MyRuntimeException {
    public NotFoundException(String msg) {
        super(msg);
    }
}
