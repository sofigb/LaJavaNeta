package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService aService;
    @Autowired
    private CustomerService cService;

    @GetMapping("")
    public ModelAndView show(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("tablesAccount");
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        mav.addObject("accountPeso", aService.findByCustomerIdCurrency(idCustomer, CurrencyType.PESO_ARG));
        mav.addObject("accountDollar", aService.findByCustomerIdCurrency(idCustomer, CurrencyType.DOLLAR));
        mav.addObject("customer", cService.findById(idCustomer).get());
        mav.addObject("href1", "account");
        mav.addObject("href", "new");
        mav.addObject("title", "Mis Cuentas");
        mav.addObject("title1", "Mis CUENTAS");
        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Long id) {
        ModelAndView mav = new ModelAndView("updateAccount");

        mav.addObject("account", aService.findById(id));
        mav.addObject("action", "save");
        mav.addObject("title1", " Cambios en Cuentas");

        return mav;
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Long id) {
        aService.deleteById(id);

        return new RedirectView("/myDashboard");

    }

    @GetMapping("/createAccount")
    public RedirectView create(HttpSession session) throws InputException {
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        aService.createAccount(CurrencyType.DOLLAR, cService.findById(idCustomer).get());

        return new RedirectView("/myDashboard");

    }

    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("account") Account account) throws Exception {

        aService.updateAlias(account.getAlias(), account.getId());

        return new RedirectView("/myDashboard");

    }

    //ALGO QUE HACE EL SUPER ADMIN
    @GetMapping("/active/{id}" + "" + "")
    public RedirectView active(@PathVariable Long id) {

        aService.active(id);
        return new RedirectView("/myDashboard");

    }
}