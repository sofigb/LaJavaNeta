package com.edu.egg.virtual_wallet.security;

import com.edu.egg.virtual_wallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LoginService loginService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder authenticator) throws Exception {
        authenticator.userDetailsService(loginService).passwordEncoder(passwordEncoder); // Under Revision
    }

    /********************* ROLE HIERARCHY NEEDS TESTING *********************/

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_ADMIN > ROLE_EMPLOYEE \n ROLE_EMPLOYEE > ROLE_CUSTOMER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    private SecurityExpressionHandler<FilterInvocation> webExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }

    /*
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler() {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy());
        return expressionHandler;
    }
     */

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .expressionHandler(webExpressionHandler())
                    .antMatchers("/", "/login", "/register", "/register/check", "/css/*", "/images/*","/assets/*").permitAll()
                    .antMatchers("/**").authenticated() //para poder usar sin login
                .and()
                .formLogin()
                    .loginPage("/login") // Route to HTML with login page - @GetMapping("/login")
                        .loginProcessingUrl("/login/check") // th:action url - @PostMapping("/login/check")
                        .usernameParameter("username") // Overriding username and password parameters
                        .passwordParameter("password")
                        .defaultSuccessUrl("/myDashboard", true) // Success URL - Should redirect to Customers profile
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