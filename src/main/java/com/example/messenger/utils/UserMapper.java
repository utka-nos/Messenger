package com.example.messenger.utils;

import com.example.messenger.dto.UserDTO;
import com.example.messenger.model.User;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;

@Slf4j
public class UserMapper {

    //TODO: СДелать через рефлексию аналогичный метод как для toDTO()
    public static User toEntity(UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setLogin(userDTO.getLogin());
        user.setMessages(userDTO.getMessages());
        user.setId(userDTO.getId());
        user.setUserPic(userDTO.getUserPic());
        return user;
    }

    public static UserDTO toDTO(User user) {
        if (user == null) return null;
        UserDTO userDTO = new UserDTO();

        Class<? extends User> userClass = user.getClass();
        Field[] userFields = userClass.getDeclaredFields();

        Class<UserDTO> userDTOClass = UserDTO.class;
        Field[] userDTOFields = userDTOClass.getDeclaredFields();

        for (Field field : userFields) {
            String fieldName = field.getName();
            Arrays.stream(userDTOFields)
                    .filter((f) -> f.getName().equals(fieldName))
                    .findFirst().ifPresent(userDTOField -> {
                        userDTOField.setAccessible(true);
                        field.setAccessible(true);
                try {
                    userDTOField.set(userDTO, field.get(user));
                } catch (IllegalAccessException e) {
                    log.warn("cant get field {{}} from user! Cause: {{}}", field.getName(), e.getMessage());
                }
            });

        }

        return userDTO;
    }

}
