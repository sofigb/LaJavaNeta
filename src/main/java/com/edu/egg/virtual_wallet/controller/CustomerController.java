package com.edu.egg.virtual_wallet.controller;



import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("registerCustomer");

        mav.addObject("customer", new Customer());
        mav.addObject("address", new Address());
        mav.addObject("contact", new Contact());
        mav.addObject("name", new Name());
        mav.addObject("login", new Login());

        return mav;
    }

    @PostMapping("/register/check")
    public RedirectView checkRegistration(@ModelAttribute("customer") Customer customer, @ModelAttribute("address") Address address,
                                          @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                          @ModelAttribute("login") Login login) throws InputException {
        customerService.createCustomer(customer, address, contact, name, login);
        return new RedirectView("/login");
    }

    @GetMapping("/myDashboard")
    public ModelAndView customerDashboard(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("myDashboard");
        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        mav.addObject("customer", customerService.returnCustomer(idCustomer));
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView customerProfile(HttpSession session) throws InputException {

        ModelAndView mav = new ModelAndView("editCustomerProfile");

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        Customer customer = customerService.returnCustomer(idCustomer);

        mav.addObject("customer", customer);
        mav.addObject("address", customer.getAddressInfo());
        mav.addObject("contact", customer.getContactInfo());
        mav.addObject("name", customer.getFullName());
        mav.addObject("username", customer.getLoginInfo().getUsername());

        return mav;
    }

    @PostMapping("/profile/edit")
    public RedirectView editCustomerProfile(@ModelAttribute Customer customer, @ModelAttribute Address address,
                                            @ModelAttribute("contact") Contact contact, @ModelAttribute("name") Name name,
                                            @RequestParam String username, HttpSession session)
            throws InputException{
        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.editCustomer(customer, idCustomer, address, contact, name, username);
        return new RedirectView("/myDashboard");
    }

    @PostMapping("/profile/delete")
    public RedirectView deleteAccount(HttpSession session) throws InputException {

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.deactivateCustomer(idCustomer);
        return new RedirectView("/login?logout=true");
    }

    @GetMapping("/profile/changePassword")
    public ModelAndView changePassword() {
        ModelAndView mav = new ModelAndView("changePassword");

        mav.addObject("postPath", "/profile/changePassword/check");
        mav.addObject("currentPassword", "");
        mav.addObject("newPassword", "");
        mav.addObject("confirmNewPassword", "");

        return mav;
    }

    @PostMapping("/profile/changePassword/check")
    public RedirectView verifyChangedPassword(@RequestParam String currentPassword, @RequestParam String newPassword,
                                              @RequestParam String confirmNewPassword, HttpSession session)
            throws  InputException {
        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.editCustomerPassword(idCustomer, currentPassword, newPassword, confirmNewPassword);
        return new RedirectView("/profile");
    }
}