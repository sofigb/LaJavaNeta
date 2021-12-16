package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import com.edu.egg.virtual_wallet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepo customerRepo;

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
                                          @ModelAttribute("login") Login login) throws VirtualWalletException {
        customerService.createCustomer(customer, address, contact, name, login);
        return new RedirectView("/login");
    }

    @GetMapping("/myDashboard")
    public ModelAndView customerDashboard(HttpSession session) throws VirtualWalletException {
        ModelAndView mav = new ModelAndView("myDashboard");
        mav.addObject("customer", customerService.returnCustomer(1));
        return mav;
    }

    @GetMapping("/profile/{idCustomer}")
    public ModelAndView customerProfile(HttpSession session, @PathVariable Integer idCustomer) throws VirtualWalletException {
        ModelAndView mav = new ModelAndView("editCustomerProfile");

        //(Integer) session.getAttribute("id")
        //Customer customer = customerService.returnCustomer(idCustomer);

        Customer customer = customerRepo.findById(idCustomer).get();

        mav.addObject("customer", customer);
        mav.addObject("address", customer.getAddressInfo());
        mav.addObject("contact", customer.getContactInfo());
        mav.addObject("name", customer.getFullName());
        mav.addObject("login", customer.getLoginInfo());

        return mav;
    }

    /*
    @ModelAttribute("address") Address address,
                                            @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                            @ModelAttribute("login") Login login,

                                             address, contact, name, login
     */

    @GetMapping("/profile/edit/{idCustomer}")
    public RedirectView editCustomerProfile(@ModelAttribute Customer customer, @PathVariable Integer idCustomer,
                                            @ModelAttribute Address address, @ModelAttribute("contact") Contact  contact,
                                            @ModelAttribute("name") Name name, @ModelAttribute("login") Login login)
            throws VirtualWalletException {
        customerService.editCustomer(customer, idCustomer, address, contact, name, login);
        return new RedirectView("/profile/" + idCustomer);
    }
}