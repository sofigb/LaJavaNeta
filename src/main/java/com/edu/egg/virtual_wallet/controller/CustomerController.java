package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/myDashboard")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AccountService accountService;

    @GetMapping("/help")
    public ModelAndView help(){
        ModelAndView mav = new ModelAndView("help-center");
        return mav;
    }

    @GetMapping
    public ModelAndView customerDashboard(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("dashboard");

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        mav.addObject("accountPeso", accountService.findByCustomerIdCurrency(idCustomer, CurrencyType.PESO_ARG));
        mav.addObject("accountDollar", accountService.findByCustomerIdCurrency(idCustomer, CurrencyType.DOLLAR));
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView customerProfile(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("editCustomerProfileDana");

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        Customer customer = customerService.returnCustomer(idCustomer);

        mav.addObject("postPath", "/myDashboard/profile/edit");
        mav.addObject("customer", customer);
        mav.addObject("address", customer.getAddressInfo());
        mav.addObject("contact", customer.getContactInfo());
        mav.addObject("name", customer.getFullName());
        mav.addObject("username", customer.getLoginInfo().getUsername());
        mav.addObject("defaultDashboardPath", "/myDashboard");

        return mav;
    }

    @PostMapping("/profile/edit")
    public RedirectView editCustomerProfile(@ModelAttribute Customer customer, @ModelAttribute Address address,
                                            @ModelAttribute("contact") Contact contact, @ModelAttribute("name") Name name,
                                            @RequestParam String username, HttpSession session) throws InputException {

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

        mav.addObject("postPath", "/myDashboard/profile/changePassword/check");
        mav.addObject("currentPassword", "");
        mav.addObject("newPassword", "");
        mav.addObject("confirmNewPassword", "");
        mav.addObject("defaultDashboardPath", "/myDashboard");

        return mav;
    }

    @PostMapping("/profile/changePassword/check")
    public RedirectView verifyChangedPassword(@RequestParam String currentPassword, @RequestParam String newPassword,
                                              @RequestParam String confirmNewPassword, HttpSession session) throws InputException {

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.editCustomerPassword(idCustomer, currentPassword, newPassword, confirmNewPassword);
        return new RedirectView("/myDashboard/profile");
    }
}