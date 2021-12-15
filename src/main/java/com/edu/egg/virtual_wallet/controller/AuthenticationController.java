package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

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
            modelAndView.setViewName("redirect:/"); // Redirect to Customers profile
        }

        return modelAndView;
    }

    // HOW TO REGISTER A CUSTOMER? Employees should not be able to register, they are given an account by a SuperAdmin

    @GetMapping("/register")
    private ModelAndView register(HttpServletRequest request, Principal principal) {
        ModelAndView mav = new ModelAndView("register");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if (flashMap != null) {
            mav.addObject("registrationSuccessMessage", flashMap.get("registrationSuccessMessage"));
            mav.addObject("registrationErrorMessage", flashMap.get("registrationErrorMessage"));
            mav.addObject("username", flashMap.get("username"));
            mav.addObject("password", flashMap.get("password"));
        } else {
            mav.addObject("loginInfo", new Login());
        }

        if (principal != null) {
            mav.setViewName("redirect:/login");
        }

        return mav;
    }

    @PostMapping("/register/check")
    public RedirectView addUser(@ModelAttribute("loginInfo") Login loginInfo, RedirectAttributes attributes) {
        try {
            loginService.createLogin(loginInfo);
            attributes.addFlashAttribute("registrationSuccessMessage", "Registration complete! Welcome!");
        } catch (InputException e) {
            attributes.addFlashAttribute("username", loginInfo.getUsername());
            attributes.addFlashAttribute("password", loginInfo.getPassword());
            attributes.addFlashAttribute("registrationErrorMessage", e.getMessage());
            return new RedirectView("/register");
        }
        return new RedirectView("/login");
    }

}