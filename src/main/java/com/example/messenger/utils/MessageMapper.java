package com.example.messenger.utils;

import com.example.messenger.dto.MessageDTO;
import com.example.messenger.model.Message;
import lombok.Data;

@Data
public class MessageMapper {

    public static Message toEntity(MessageDTO messageDTO) {
        Message message = new Message();
        message.setId(messageDTO.getId());
        message.setText(messageDTO.getText());
        message.setCreationTime(messageDTO.getTime());
        message.setAuthorId(messageDTO.getAuthorId());
        return message;
    }

    public static MessageDTO toDTO(Message message) {
        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setId(message.getId());
        messageDTO.setTime(message.getCreationTime());
        messageDTO.setText(message.getText());
        messageDTO.setAuthorId(message.getAuthorId());
        return messageDTO;
    }
}
