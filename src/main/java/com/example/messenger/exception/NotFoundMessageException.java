package com.example.messenger.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundMessageException extends MyRuntimeException {
    public NotFoundMessageException(String msg) {
        super(msg);
    }
}
