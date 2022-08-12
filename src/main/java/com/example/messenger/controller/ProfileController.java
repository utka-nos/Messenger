package com.example.messenger.controller;

import com.example.messenger.argument_resolver.CurrentUser;
import com.example.messenger.dto.UserDTO;
import com.example.messenger.dto.UserDTOViews;
import com.example.messenger.utils.ControllerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private String devServerUrl;

    @GetMapping("/profile")
    public String myProfile(Model model, @CurrentUser UserDTO curUser) throws JsonProcessingException, IllegalAccessException {

        model.addAttribute("frontendData", ControllerUtils.getFrontendData(curUser, UserDTOViews.FullInfo.class));
        model.addAttribute("isDevMode", "DEV".equals(profile));
        model.addAttribute("devServerUrl", devServerUrl);

        return "profile-page/profile_page";
    }

}
