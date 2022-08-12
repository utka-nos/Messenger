package com.example.messenger.service;

import com.example.messenger.dto.EventType;
import com.example.messenger.dto.MessageDTO;
import com.example.messenger.dto.ObjectType;
import com.example.messenger.exception.NotAuthorizedException;
import com.example.messenger.exception.NotFoundMessageException;
import com.example.messenger.exception.NotFoundUserException;
import com.example.messenger.model.Message;
import com.example.messenger.model.User;
import com.example.messenger.repo.MessageRepo;
import com.example.messenger.utils.MessageMapper;
import com.example.messenger.utils.UserBuilder;
import com.example.messenger.utils.WsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.NoSuchMessageException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private SequenceService seqService;

    @Autowired
    private WsSender wsSender;

    @Autowired
    private UserService userService;

    public List<MessageDTO> getMessages() {
        return messageRepo
                .findAll()
                .stream()
                .map(MessageMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public MessageDTO postMessage(MessageDTO message, OAuth2User user)
            throws NotAuthorizedException, NotFoundUserException {

        if (user == null) throw new NotAuthorizedException("User not authorized!");
        User author = userService.getUserByOAuth2User(user);
        message.setAuthorId(author.getId());

        message.setTime(LocalDateTime.now());
        message.setId(seqService.getId(Message.class).toString());

        Message savedMessage = messageRepo.save(MessageMapper.toEntity(message));

        author.getMessages().add(savedMessage);

        userService.saveUser(author);
        // Отправляем сообщение в WebSocket
        wsSender.getSender(ObjectType.MESSAGE).accept(EventType.CREATE, savedMessage);

        return MessageMapper.toDTO(savedMessage);
    }

    public String deleteMessage(String messageId, OAuth2User oAuth2User)
            throws NotAuthorizedException, NotFoundUserException, NotFoundMessageException {

        if (oAuth2User == null) throw new NotAuthorizedException("Author not authorized");

        Message messageById = messageRepo.findById(messageId)
                .orElseThrow(() -> new NotFoundMessageException("No message to delete"));

        User user = userService.getUserByOAuth2User(oAuth2User);

        if (!messageById.getAuthorId().equals(user.getId())) {
            throw new NotAuthorizedException("У вас нет прав на удаление этого сообщения!");
        }

        user.getMessages().remove(messageById);

        userService.saveUser(user);
        messageRepo.delete(messageById);

        wsSender.getSender(ObjectType.MESSAGE).accept(EventType.DELETE, messageById);

        return messageId;
    }

    public MessageDTO putMessage(String messageId, MessageDTO messageToPut, OAuth2User user)
            throws NotFoundMessageException, NotAuthorizedException {

        Message message = messageRepo.findById(messageId)
                .orElseThrow(() -> new NotFoundMessageException("No such message Id!"));

        User trueUser = UserBuilder.buildUserFromAttributes(user.getAttributes());

        if (!message.getAuthorId().equals(trueUser.getId()))
            throw new NotAuthorizedException(String.format(
                    "User with id %s can't edit message with id %s. It is not his message!",
                    trueUser.getId(),
                    messageId
            ));

        if (!messageId.equals(messageToPut.getId()))
            throw new NoSuchMessageException("Path variable and message id are different!");

        message.setText(messageToPut.getText());

        messageRepo.save(message);

        wsSender.getSender(ObjectType.MESSAGE).accept(EventType.UPDATE, message);

        return MessageMapper.toDTO(message);
    }
}
