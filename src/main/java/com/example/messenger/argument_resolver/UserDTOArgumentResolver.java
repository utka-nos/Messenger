package com.example.messenger.argument_resolver;

import com.example.messenger.dto.UserDTO;
import com.example.messenger.exception.NotFoundUserException;
import com.example.messenger.model.User;
import com.example.messenger.repo.UserRepo;
import com.example.messenger.utils.UserBuilder;
import com.example.messenger.utils.UserMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * Handle annotation @CurrentUser in parameter in @Controller methods
 */

@Slf4j
@AllArgsConstructor
public class UserDTOArgumentResolver implements HandlerMethodArgumentResolver {

    private final UserRepo userRepo;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // Если призываем UserDTO и стоит аннотация @CurrentUser, то обрабатываем это случай здесь
        Class<?> parameterType = parameter.getParameterType();
        if (!parameterType.equals(UserDTO.class)) return false;

        CurrentUser curUserAnnotation = parameter.getParameterAnnotation(CurrentUser.class);
        return curUserAnnotation != null;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {

        // берем из секьюрити информацию о текущем пользователе
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        //если разлогинены (нет текущего пользователя в секьбрити), то ничего не возвращаем
        if (authentication == null) return null;

        try {
            // пытаемся получить id текущего пользователя из информации от секьюрити
            String userId = UserBuilder.getUserId(authentication);
            //пытаемся найти пользователя по id в нашем локальном хранилище
            User byId = userRepo.findById(userId).orElseThrow(() ->
                    new NotFoundUserException("Current user is not available. Please, logout and login again!"));
            return UserMapper.toDTO(byId);
        } catch (ClassCastException | NullPointerException ex) {
            log.info("Don't getting arguments because of not authorized (it is normal) {{}}", ex.getMessage());
            return null;
        }
    }
}
