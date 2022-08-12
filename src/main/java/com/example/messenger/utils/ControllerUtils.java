package com.example.messenger.utils;

import com.example.messenger.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class ControllerUtils {

    public static Map<String, Object> getFrontendData(UserDTO userDTO) {
        Map<String, Object> frontendData = new HashMap<>();
        frontendData.put("profile", userDTO);
        return frontendData;
    }

    public static Map<String, Object> getFrontendData(UserDTO userDTO, Class<?> jsonView) throws IllegalAccessException, JsonProcessingException {
        Map<String, Object> frontendData = new HashMap<>();
        Map<String, Object> userData = new HashMap<>();

        if (userDTO == null) {
            frontendData.put("profile", null);
            return frontendData;
        }

        Class<? extends UserDTO> userDTOClass = userDTO.getClass();
        Field[] declaredFields = userDTOClass.getDeclaredFields();
        for (Field field : declaredFields) {
            if (field.isAnnotationPresent(JsonView.class)) {
                JsonView annotation = field.getAnnotation(JsonView.class);
                Class<?>[] views = annotation.value();
                for (Class<?> view : views) {
                    if (view.equals(jsonView) || view.isAssignableFrom(jsonView)) {
                        field.setAccessible(true);
                        Object value = field.get(userDTO);
                        userData.put(field.getName(), value);
                    }
                }
            }
        }

        frontendData.put("profile", userData);
        return frontendData;
    }

}
