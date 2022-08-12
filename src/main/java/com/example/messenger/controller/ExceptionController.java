package com.example.messenger.controller;

import com.example.messenger.dto.ErrorDTO;
import com.example.messenger.exception.MyRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class ExceptionController {


    @ExceptionHandler(MyRuntimeException.class)
    public ResponseEntity<ErrorDTO> handleErrors(Exception ex) {
        log.error("{{}}", ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO();
        int status = getStatus(ex);

        errorDTO.setTime(LocalDateTime.now());
        errorDTO.setText(ex.getMessage());
        errorDTO.setStatus(status);

        return ResponseEntity.status(status).body(errorDTO);
    }

    private int getStatus(Exception ex) {
        Class<? extends Exception> exClass = ex.getClass();
        ResponseStatus annotation = exClass.getDeclaredAnnotation(ResponseStatus.class);
        return annotation != null ? annotation.value().value() : 400;
    }

}
