package com.example.messenger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    // В этом сервисе прописана логика сохранения пользователя
    @Autowired
    private OAuth2AuthorizedClientService clientService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .mvcMatchers("/", "/bundle.js", "/error**", "/failureLogin", "/*.css", "/*.js", "/*.map").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout(l-> l.logoutSuccessUrl("/").permitAll())
                .oauth2Login()
                // после удачной авторизации будет перенаправлять сюда, если до этого мы не перешли на нужную страницу.
                // Если добавить еще один аргумент true - то в любом случае после авторизации навправляет сюда
                .defaultSuccessUrl("/successLogin", true)
                // После неудачной авторизации перенаправляет сюда
                .failureUrl("/failureLogin")
                // задаем, что наша страница логина будет по данному url
                .loginPage("/login")
                // Настраиваем авторизационный эндпоинт
                .authorizationEndpoint()
                // Так можно задать какой-то другой адрес для ссылки на авторизацию
                //.baseUri("/custom/url/for/auth")
                // Что-то можно настроить, вставляем свой бин сюда (ниже)
                /*.authorizationRequestRepository(authorizationRequestRepository())*/
                .and()
                // Можно настроить redirection endpoint, но нам не надо
                /*.redirectionEndpoint()
                    .baseUri()*/
                // Настраиваем сохранение пользователей
                // Этот метод будет вызываться при успешной авторизации
                .authorizedClientService(clientService)
                // Метод, который будет вызываться при вызове userInfoEndpoint. Github не вызывает его.
                /*.userInfoEndpoint()
                .oidcUserService(this.oidcUserService())
                .and()*/
                .and()
                .csrf().disable();
    }


}
