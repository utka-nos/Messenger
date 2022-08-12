package com.example.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Data
@Document
public class User {

    @MongoId
    private String id;

    private String login;

    private String name;

    private String userPic;

    private String email;

    private String defaultUserPic;

    private String gender;

    private LocalDateTime lastVisit;

    @DBRef
    private List<Message> messages = new ArrayList<>();

}
