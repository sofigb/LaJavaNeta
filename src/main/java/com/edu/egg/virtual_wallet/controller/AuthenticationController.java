package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Login;

import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
                              Principal principal, HttpSession session) throws InputException {
        ModelAndView modelAndView = new ModelAndView("login");

        // Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));

        if (error != null) {
            modelAndView.addObject("error", "Watch out! Invalid username or password");
        }

        if (logout != null) {
            // timestamp
            modelAndView.addObject("logout", "You've signed out, sorry to see you go.");
        }

        if (principal != null) {
            modelAndView.setViewName("redirect:/myDashboard"); // Redirect to Customers profile
        }

        return modelAndView;
    }
}