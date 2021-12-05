package com.edu.egg.virtual_wallet.security;

import com.edu.egg.virtual_wallet.service.LoginServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginServiceImpl loginService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticator) throws Exception {
        authenticator.userDetailsService(loginService).passwordEncoder(passwordEncoder); // Under Revision
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                    .antMatchers("/", "/login", "/register", "/register/check", "/css/*", "/images/*","/assets/*").permitAll()
                    .antMatchers("/**").authenticated() //.permitAll() para poder usar sin login
                .and()
                .formLogin()
                    .loginPage("/login") // Route to HTML with login page - @GetMapping("/login")
                        .loginProcessingUrl("/login/check") // th:action url - @PostMapping("/login/check")
                        .usernameParameter("username") // Overriding username and password parameters
                        .passwordParameter("password")
                        .defaultSuccessUrl("/home", true) // Success URL under revision
                        .failureUrl("/login?error=true")
                        .permitAll()
                .and()
                    .logout()
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                        .deleteCookies("JSESSIONID")
                .and()
                    .csrf()
                    .disable();
    }
}