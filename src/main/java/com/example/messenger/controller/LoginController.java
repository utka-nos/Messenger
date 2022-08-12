package com.example.messenger.controller;

import com.example.messenger.argument_resolver.CurrentUser;
import com.example.messenger.dto.UserDTO;
import com.example.messenger.dto.UserDTOViews;
import com.example.messenger.service.LoginService;
import com.example.messenger.utils.ControllerUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Value("${spring.profiles.active}")
    private String profile;

    @Autowired
    private String devServerUrl;

    @GetMapping("/login")
    public String getLoginPage(Model model, @CurrentUser UserDTO userDTO) throws JsonProcessingException, IllegalAccessException {

        model.addAttribute("urls", loginService.getAuthUrls());
        model.addAttribute("isDevMode", "DEV".equals(profile));
        model.addAttribute("devServerUrl", devServerUrl);

        model.addAttribute("frontendData", ControllerUtils.getFrontendData(userDTO, UserDTOViews.LoginUserPicId.class));

        return "login-page/login_page";
    }



    @GetMapping("/successLogin")
    public String getLoginInfo() {
        return "redirect:/";
    }

}
