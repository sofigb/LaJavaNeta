package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Login;

import com.edu.egg.virtual_wallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;

@Controller
public class AuthenticationController {


    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout, Principal principal) {
        ModelAndView modelAndView = new ModelAndView("login");

        if (error != null) {
            modelAndView.addObject("error", "Watch out! Invalid username or password");
        }

        if (logout != null) {
            modelAndView.addObject("logout", "You've signed out, sorry to see you go.");
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/myDashboard"); // Redirect to Customers profile
        }

        return modelAndView;
    }

   
}