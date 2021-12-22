package com.edu.egg.virtual_wallet.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class AppConfig {

    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder(); }
}