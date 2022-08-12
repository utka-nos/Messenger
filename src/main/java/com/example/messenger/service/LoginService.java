package com.example.messenger.service;

import com.example.messenger.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class LoginService {

    private static final String authorizationRequestBaseUri = "oauth2/authorization";

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    public Map<String, String> getAuthUrls() {
        Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
        try{
            // Почти в любом случае реализации InMemoryClientRegistrationRepository -
            // Этот класс из Spring-boot-autoConfigurer
            // интерфейса ClientRegistrationRepository будет достаточно
            // Он инициализирует регистрации при запуске программы и до следующего запуска будет крутить
            // заявленные registrations
            InMemoryClientRegistrationRepository clientRegistrations =
                    ((InMemoryClientRegistrationRepository) this.clientRegistrationRepository);
            clientRegistrations.forEach(reg ->
                    oauth2AuthenticationUrls.put(reg.getClientName(),
                            authorizationRequestBaseUri + "/" + reg.getRegistrationId()));
        } catch (Exception ex) {
            log.error("Can't cast clientRegistrationRepository to InMemoryClientRegistrationRepository - {{}}", ex.getMessage());
        }

        return oauth2AuthenticationUrls;

    }

}
