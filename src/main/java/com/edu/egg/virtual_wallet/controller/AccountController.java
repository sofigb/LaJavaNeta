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

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService aService;
    @Autowired
    private CustomerService cService;

    @GetMapping("/{id}")
    public ModelAndView show(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("tablesAccount");

        mav.addObject("listaOb", aService.findByCustomerId(id));
        mav.addObject("customer", cService.findById(id).get());
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
        Integer idCustomer = (aService.findById(id).getCustomer().getId());
        aService.deleteById(id);

        return new RedirectView("/account/"+idCustomer);

    }

    @GetMapping("/createAccount/{id}")
    public RedirectView create(@PathVariable Integer id) {

        aService.createAccount(CurrencyType.DOLLAR, cService.findById(id).get());

        return new RedirectView("/account/{id}");

    }

    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("account") Account account, @ModelAttribute("customer") Customer customer) throws Exception {

        aService.updateAlias(account.getAlias(), account.getId());
        Integer idCustomer = (aService.findById(account.getId()).getCustomer().getId());
        return new RedirectView("/account/" + idCustomer);

    }
    
    
    //ALGO QUE HACE EL SUPER ADMIN
    @GetMapping("/active/{id}")
    public RedirectView active(@PathVariable Long id) {

        aService.active(id);
        Integer idCustomer = (aService.findById(id).getCustomer().getId());
        return new RedirectView("/account/" + idCustomer);

    }
}
