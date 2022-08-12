package com.example.messenger.controller;

import com.example.messenger.dto.UserDTO;
import com.example.messenger.dto.UserDTOViews;
import com.example.messenger.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    @JsonView(UserDTOViews.LoginUserPicId.class)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }

}
