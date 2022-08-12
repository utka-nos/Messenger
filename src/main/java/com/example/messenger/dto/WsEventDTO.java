package com.example.messenger.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonView(MessageViews.IdText.class)
public class WsEventDTO {
    private ObjectType objectType;
    private EventType eventType;

    @JsonRawValue // Встраиваем строку как часть json'а
    private String body;
}
