package com.example.messenger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
public class OtherConfig {

    @Bean
    public String devServerUrl() {
        try {
            InetAddress ia = InetAddress.getLocalHost();
            String hostAddress = ia.getHostAddress();
            return String.format("%s:8081", hostAddress);
        } catch (UnknownHostException ex) {
            return "127.0.0.1:8081";
        }
    }

}
