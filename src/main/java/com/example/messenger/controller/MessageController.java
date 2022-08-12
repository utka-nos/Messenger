package com.example.messenger.controller;

import com.example.messenger.dto.MessageDTO;
import com.example.messenger.dto.MessageViews;
import com.example.messenger.exception.NotAuthorizedException;
import com.example.messenger.exception.NotFoundMessageException;
import com.example.messenger.exception.NotFoundUserException;
import com.example.messenger.service.MessageService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/message")
@CrossOrigin
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;

    @GetMapping
    @JsonView(MessageViews.FullMessage.class)
    public List<MessageDTO> getAllMessages() {
        return messageService.getMessages();
    }

    @PostMapping
    @JsonView(MessageViews.FullMessage.class)
    public ResponseEntity<Object> postMessage(
            @RequestBody MessageDTO message,
            @AuthenticationPrincipal OAuth2User user
    ) throws NotAuthorizedException, NotFoundUserException {
        MessageDTO savedMessage = messageService.postMessage(message, user);
        return ResponseEntity.status(201) .body(savedMessage);
    }

    @PutMapping("/{id}")
    @JsonView(MessageViews.IdText.class)
    public  ResponseEntity<Object> updateMessage(
            @PathVariable("id") String messageId,
            @RequestBody MessageDTO messageToPut,
            @AuthenticationPrincipal OAuth2User user
    ) throws NotFoundMessageException, NotAuthorizedException {
        MessageDTO message = messageService.putMessage(messageId, messageToPut, user);
        return ResponseEntity.status(200).body(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMessage(
            @PathVariable("id") String messageId,
            @AuthenticationPrincipal OAuth2User user
    ) throws NotAuthorizedException, NotFoundMessageException, NotFoundUserException {
        String deletedMessageId = messageService.deleteMessage(messageId, user);
        return ResponseEntity.status(204).body(deletedMessageId);
    }

    /*@MessageMapping("/deleteMessage/{messageId}")
    @SendTo("/topic/activity")
    public ResponseEntity<Object> deleteMessageWS(
            Authentication auth,
            @DestinationVariable String messageId
    ) {
        try {
            String deletedMessageId = messageService.deleteMessage(messageId, ((OAuth2AuthenticationToken)auth).getPrincipal());
            return ResponseEntity.status(204).header("operation", "DELETE").body(deletedMessageId);
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NotFoundMessageException | NotFoundUserException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }*/


    /*@MessageMapping("/postMessage")
    @SendTo("/topic/activity")
    public ResponseEntity<Object> postMessageWS(
            MessageDTO message,
            Authentication auth
    ) {
        try {
            MessageDTO messageDTO = messageService.postMessage(message, (OAuth2User) auth.getPrincipal());
            return ResponseEntity.status(201).header("operation", "POST").body(messageDTO);
        } catch (NotAuthorizedException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (NotFoundUserException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }*/


}
