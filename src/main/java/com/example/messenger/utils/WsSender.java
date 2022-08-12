package com.example.messenger.utils;

import com.example.messenger.dto.EventType;
import com.example.messenger.dto.ObjectType;
import com.example.messenger.dto.WsEventDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.function.BiConsumer;

@Component
@Slf4j
public class WsSender {

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ObjectMapper objectMapper;

    public <T> BiConsumer<EventType, T> getSender(ObjectType objectType) {

        return (EventType eventType, T payload) -> {
            String value = null;
            try {
                value = objectMapper.writeValueAsString(payload);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            template.convertAndSend(
                    "/topic/activity",
                    new WsEventDTO(objectType, eventType, value)
            );
        };
    }

}
