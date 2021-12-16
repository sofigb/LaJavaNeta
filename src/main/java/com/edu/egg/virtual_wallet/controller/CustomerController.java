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
        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        mav.addObject("customer", customerService.returnCustomer(idCustomer));
        return mav;
    }

    @GetMapping("/profile")
    public ModelAndView customerProfile(HttpSession session) throws VirtualWalletException {
        ModelAndView mav = new ModelAndView("editCustomerProfile");

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        Customer customer = customerRepo.findById(idCustomer).get();

        mav.addObject("customer", customer);
        mav.addObject("address", customer.getAddressInfo());
        mav.addObject("contact", customer.getContactInfo());
        mav.addObject("name", customer.getFullName());
        mav.addObject("login", customer.getLoginInfo());

        return mav;
    }

    @PostMapping("/profile/edit")
    public RedirectView editCustomerProfile(@ModelAttribute Customer customer, @ModelAttribute Address address,
                                            @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                            @ModelAttribute("login") Login login, HttpSession session)
            throws VirtualWalletException {

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.editCustomer(customer, idCustomer, address, contact, name, login, true);
        return new RedirectView("/myDashboard");
    }

    /*@PostMapping("/profile/delete")
    public RedirectView deleteAccount(@ModelAttribute Customer customer, @ModelAttribute Address address,
                                      @ModelAttribute("contact") Contact  contact, @ModelAttribute("name") Name name,
                                      @ModelAttribute("login") Login login, HttpSession session) throws VirtualWalletException{

        Integer idCustomer = customerService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        customerService.editCustomer(customer, idCustomer, address, contact, name, login, true);
        //customerService.deactivateCustomer(idCustomer, false);
        return new RedirectView("/login?logout=true");
    }*/
}