package com.example.messenger.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorDTO {

    private String text;
    private int status;
    private LocalDateTime time;

}
