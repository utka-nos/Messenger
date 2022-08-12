package com.example.messenger.service;

import com.example.messenger.dto.UserDTO;
import com.example.messenger.exception.NotAuthorizedException;
import com.example.messenger.exception.NotFoundUserException;
import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepo;
import com.example.messenger.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UserService{

    @Autowired
    private UserRepo userRepo;

    public User getUserByOAuth2User(OAuth2User userPrincipal) throws NotFoundUserException {
        if (userPrincipal == null) return null;
        // Для google
        String id = userPrincipal.getAttribute("sub");
        // Для github
        if (id == null) {
            Object objId = userPrincipal.getAttribute("id");
            id = objId == null ?
                    "-1" : objId.toString();
        }
        String finalId = id;
        return userRepo.findById(id).orElseThrow(() -> new NotFoundUserException("No user with id=" + finalId));
    }

    public User saveUser(User author) {
        return userRepo.save(author);
    }

    public Map<String, Object> getFrontendData(UserDTO userDTO) {
        Map<String, Object> frontendData = new HashMap<>();
        frontendData.put("profile", userDTO);
        return frontendData;
    }

    public List<UserDTO> getAllUsers() {
        return userRepo.findAll().stream().map(UserMapper::toDTO).collect(Collectors.toList());
    }
}
