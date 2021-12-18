package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.security.MyAuthenticationSuccessHandler;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;
import java.security.Principal;

@Controller
public class AuthenticationController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private CustomerService customerService;

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(required = false) String error, @RequestParam(required = false) String logout,
                              Principal principal, HttpSession session, Authentication authentication) {

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
            MyAuthenticationSuccessHandler redirectURL = new MyAuthenticationSuccessHandler();
            modelAndView.setViewName("redirect:" + redirectURL.determineTargetUrl(authentication)); // Redirect to Customers profile
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("signup");

        mav.addObject("customer", new Customer());
        mav.addObject("address", new Address());
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("username", "");

        return mav;
    }

    @PostMapping("/register/check")
    public RedirectView checkRegistration(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address,
                                          @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                          @RequestParam String username) throws InputException {
        customerService.createCustomer(customer, address, contact, name, username);
        return new RedirectView("/login");
    }
}