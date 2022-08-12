package com.example.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Data
@Document
public class Message {

    @MongoId
    private String id;

    private String text;

    private LocalDateTime creationTime;


    private String authorId;
}
