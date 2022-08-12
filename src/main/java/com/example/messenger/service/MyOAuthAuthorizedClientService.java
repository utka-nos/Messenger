package com.example.messenger.service;

import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepo;
import com.example.messenger.utils.UserBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Slf4j
public class MyOAuthAuthorizedClientService implements OAuth2AuthorizedClientService {

    private final UserRepo userRepo;

    public MyOAuthAuthorizedClientService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public <T extends OAuth2AuthorizedClient> T loadAuthorizedClient(String clientRegistrationId, String principalName) {
        log.info("client registration id : {{}}, principal name : {{}}", clientRegistrationId, principalName);
        return null;
    }

    @Override
    public void saveAuthorizedClient(OAuth2AuthorizedClient authorizedClient, Authentication principal) {
        Map<String, Object> attributes = ((OAuth2User) principal.getPrincipal()).getAttributes();

        //github attribute
        Object id = attributes.get("id");
        if (id == null) {
            id = attributes.get("sub"); //google attribute
        }

        /// Сравниваем информацию из OAuth2 сервера и сохраненную у нас информацию
        User userFromAttr = UserBuilder.buildUserFromAttributes(attributes);
        User user = userRepo.findById(id.toString()).orElse(userFromAttr);
        // Если обновился аватар, то обновляем и у нас
        updateDefaultUserPic(user, userFromAttr);

        // если как-то обновился логин, то меняем
        // (для случая, когда логин был налл или пользователь только зарегистрировался у нас)
        // В дальнейшем здесь менять его не будем, только если login == null
        updateLogin(user, userFromAttr);

        // TODO: Сделать сравнение и обновение остальной информации
        userRepo.save(user);
    }

    private void updateLogin(User user, User userFromAttr) {
        if (user.getLogin() == null) {
            if (userFromAttr.getLogin() != null) {
                user.setLogin(userFromAttr.getLogin());
            }
        }
    }

    private void updateDefaultUserPic(User user, User userFromAttr) {
        if (user.getDefaultUserPic() == null) {
            if (userFromAttr.getDefaultUserPic() != null) {
                user.setDefaultUserPic(userFromAttr.getDefaultUserPic());
            }
        }
        else if (!user.getDefaultUserPic().equals(userFromAttr.getDefaultUserPic())) {
            user.setDefaultUserPic(userFromAttr.getDefaultUserPic());
        }
    }

    @Override
    public void removeAuthorizedClient(String clientRegistrationId, String principalName) {
        log.info("client registration id : {{}}, principal name : {{}}", clientRegistrationId, principalName);
    }
}
