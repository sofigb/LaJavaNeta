package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.*;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

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
            modelAndView.addObject("error", "Usuario o contraseña incorrectos");
        }

        if (logout != null) {
            // timestamp
            modelAndView.addObject("logout", "Has salido de AgroPay");
        }

        if (principal != null) {
            MyAuthenticationSuccessHandler redirectURL = new MyAuthenticationSuccessHandler();
            modelAndView.setViewName("redirect:" + redirectURL.determineTargetUrl(authentication)); // Redirect to Customers profile
        }
        return modelAndView;
    }

    @GetMapping("/register")
    public ModelAndView register(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("signup");

        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if(flashMap != null){
            mav.addObject("error", flashMap.get("error"));

            mav.addObject("customer", flashMap.get("customer"));
            mav.addObject("address",  flashMap.get("address"));
            mav.addObject("contact",  flashMap.get("contact"));
            mav.addObject("name",  flashMap.get("name"));
            mav.addObject("login",  flashMap.get("login"));
        }else{
            mav.addObject("customer", new Customer());
            mav.addObject("address", new Address());
            mav.addObject("contact", new Contact());
            mav.addObject("name", new Name());
            mav.addObject("login", new Login());
        }

        return mav;
    }

    @PostMapping("/register/check")
    public RedirectView checkRegistration(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address,
                                          @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                          @ModelAttribute("login") Login login, RedirectAttributes attributes) {
        try {
            customerService.createCustomer(customer, address, contact, name, login);

            return new RedirectView("/login");

        } catch(Exception e){
            attributes.addFlashAttribute("error",e.getMessage());
            attributes.addFlashAttribute("customer",customer);
            attributes.addFlashAttribute("address",address);
            attributes.addFlashAttribute("contact",contact);
            attributes.addFlashAttribute("name",name);
            attributes.addFlashAttribute("login",login);

            return new RedirectView("/register");
        }
    }
}