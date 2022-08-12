package com.example.messenger.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document
@Data
public class Sequence {

    @MongoId
    private String id;

    private Long curId;

}
