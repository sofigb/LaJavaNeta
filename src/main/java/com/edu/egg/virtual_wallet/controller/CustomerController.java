package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.model.entity.Customer;
import com.edu.egg.virtual_wallet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.edu.egg.virtual_wallet.model.dto.CustomerDTO;
import  com.edu.egg.virtual_wallet.model.mapper.CustomerMapper;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping("/register")
    public ModelAndView registrer() {
        ModelAndView mav = new ModelAndView("registerUser");
        mav.addObject("customer", new CustomerDTO());
        return mav;
    }

    @PostMapping("/register/check")
    public RedirectView create(@ModelAttribute("customer") CustomerDTO customerDTO) throws VirtualWalletException {
        customerService.createCustomer(customerMapper.convertToCustomer(customerDTO));
        return new RedirectView("/login");
    }

    @GetMapping("/home")
    public ModelAndView home(HttpSession session) throws VirtualWalletException {
        ModelAndView mav = new ModelAndView("homePage");
        Customer customer = customerService.returnCustomer((Integer) session.getAttribute("id"));
        mav.addObject("customer", customerMapper.convertToCustomerDTO(customer));

        return mav;
    }

    @GetMapping("/profile/edit")
    public ModelAndView showProfile(HttpSession session) throws VirtualWalletException {
        ModelAndView mav = new ModelAndView("editCustomer");
        Customer customer = customerService.returnCustomer((Integer) session.getAttribute("id"));
        mav.addObject("customer", customerMapper.convertToCustomerDTO(customer)); // CUSTOMER-DTO SENDS ID VALUES
        return mav;
    }

    @PostMapping("/profile/edit/check")
    public RedirectView edit(@ModelAttribute("customer") CustomerDTO customerDTO) throws VirtualWalletException {
        customerService.editCustomer(customerMapper.convertToCustomer(customerDTO)); // CUSTOMER-DTO ARRIVES WITH NULL ID VALUES
        return new RedirectView("/profile/edit");
    }
}