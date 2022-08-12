package com.example.messenger.dto;

import com.example.messenger.model.Message;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {

    @JsonView(UserDTOViews.LoginUserPicId.class)
    private String id;

    @JsonView(UserDTOViews.LoginUserPicId.class)
    private String login;

    @JsonView(UserDTOViews.FullInfo.class)
    private String email;

    @JsonView(UserDTOViews.FullInfo.class)
    private String name;

    @JsonView(UserDTOViews.FullInfo.class)
    private List<Message> messages;

    @JsonView(UserDTOViews.LoginUserPicId.class)
    private String userPic;

    @JsonView(UserDTOViews.LoginUserPicId.class)
    private String defaultUserPic;

}
