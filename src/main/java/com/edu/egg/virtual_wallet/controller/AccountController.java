package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;
import org.springframework.stereotype.Controller;

@Controller
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private TransactionService tService;
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

    //COSAS DE DANI

    @GetMapping("info/{id}")     // para enviar a back DANIEL MOSTRAR FICHA DE CUENTA
    public ModelAndView showAccountInfo(@PathVariable Long id, HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("account-info");
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        if (flashMap != null) {
            mav.addObject("success", flashMap.get("aliasSuccess"));
            mav.addObject("error", flashMap.get("aliasError"));
        }

        mav.addObject("cuentas", aService.accountList());
        mav.addObject("cuenta", aService.findById(id));
        mav.addObject("listT", tService.showAllByAccountId(id));
        return mav;
    }

    @PostMapping("/saveAlias")
    public RedirectView saveAliasChanges(@ModelAttribute("account") Account account, RedirectAttributes attributes) throws Exception {

        try{
            aService.updateAlias(account.getAlias(), account.getId());
            attributes.addFlashAttribute("aliasSuccess", "Alias modificado exitosamente");
        } catch (Exception e){
            attributes.addFlashAttribute("aliasError", e.getMessage());
        }

        return new RedirectView("/account/info/" + account.getId());
    }

    @GetMapping("/giftNewAccount")
    public RedirectView giftNewAccount(HttpSession session) throws InputException {
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        aService.giftNewCustomer(idCustomer);
        return new RedirectView("/myDashboard");

    }
}