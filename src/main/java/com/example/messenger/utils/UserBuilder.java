package com.example.messenger.utils;

import com.example.messenger.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Map;

public class UserBuilder {

    public static User buildUserFromAttributes(Map<String, Object> attributes) {
        User user = new User();

        //google
        Object id = attributes.get("sub");
        if (id == null) {
            //github
            id = attributes.get("id");
            user.setDefaultUserPic((String) attributes.get("avatar_url"));
            user.setLogin((String) attributes.get("login"));
        }
        //google
        else {
            user.setDefaultUserPic((String) attributes.get("picture"));
            user.setLogin((String) attributes.get("name"));
        }

        user.setEmail((String) attributes.get("email"));
        user.setName((String) attributes.get("name"));

        user.setId(id.toString());

        return user;
    }

    public static String getUserId(Authentication auth) {
        Map<String, Object> attributes = ((OAuth2User) auth.getPrincipal()).getAttributes();
        return getUserId(attributes);
    }

    public static String getUserId(Map<String, Object> attributes) {
        Object id = attributes.get("sub");
        if (id == null) {
            id = attributes.get("id");
        }
        return id.toString();
    }
}
