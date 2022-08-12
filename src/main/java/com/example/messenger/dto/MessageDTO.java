package com.example.messenger.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    @JsonView(MessageViews.IdText.class)
    private String id;

    @JsonView(MessageViews.IdText.class)
    @JsonProperty("text")
    private String text;

    @JsonView(MessageViews.FullMessage.class)
    /*@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")*/
    private LocalDateTime time;

    @JsonView(MessageViews.FullMessage.class) 
    private String authorId;

}
