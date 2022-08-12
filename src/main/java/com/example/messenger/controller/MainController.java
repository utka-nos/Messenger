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
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    // Определим, какой профайл запущен
    // Если не указать, то не запустится!
    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private String devServerUrl;

    @RequestMapping(value = "/")
    public String index(
            Model model,
            @CurrentUser UserDTO curUserDTO
    ) throws JsonProcessingException, IllegalAccessException {

        model.addAttribute("isDevMode", "DEV".equals(profile));
        model.addAttribute("frontendData", ControllerUtils.getFrontendData(curUserDTO, UserDTOViews.LoginUserPicId.class));
        model.addAttribute("devServerUrl", devServerUrl);

        return "main-page/index";
    }
}
