
package com.edu.egg.virtual_wallet.controller;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.PayeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payee")
public class PayeeController{

    @Autowired
    private PayeeService pService;
    @Autowired
    private CustomerService cService;

    @GetMapping("")
    public ModelAndView show(HttpSession session) throws InputException {
        ModelAndView mav = new ModelAndView("tables");
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        mav.addObject("listaOb", pService.findByCustomerIdList(idCustomer));
        mav.addObject("href", "register");
        mav.addObject("title", "a Contactos Frecuentes");
        mav.addObject("title1", "Contactos Frecuentes");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("registerPayee");

        mav.addObject("payee", new Payee());
        mav.addObject("action", "create");
        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("updatePayee");

        mav.addObject("objeto", pService.findById(id));
        mav.addObject("action", "save");
        mav.addObject("title1", " Cambios en Contacto frecuente");
        return mav;
    }

    @GetMapping("/active/{id}")
    public RedirectView active(@PathVariable Integer id) {
        pService.active(id);
        return new RedirectView("/payee");
    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {
        pService.deleteById(id);
        return new RedirectView("/payee");
    }

    @PostMapping("/create")
    public RedirectView create(@ModelAttribute("payee") Payee payee, HttpSession session) throws InputException {
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        pService.create(payee, idCustomer);
        return new RedirectView("/payee");
    }

    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("payee") Payee payee) throws InputException {

        pService.update(payee);
        return new RedirectView("/payee");
    }
// dani cosas
@GetMapping("/myContactList")
public ModelAndView registerr(HttpSession session) throws InputException {
    ModelAndView mav = new ModelAndView("payee-list");

    Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));

    mav.addObject("payeeList", pService.findByCustomerIdList(idCustomer));
    mav.addObject("payee", new Payee());

    return mav;
}

    @PostMapping("/createPayee")
    public RedirectView saveAliasChanges(@ModelAttribute("payee") Payee payee, HttpSession session, RedirectAttributes attributes) throws Exception {

        try{
            Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
            pService.createDani(payee, idCustomer);
            // attributes.addFlashAttribute("aliasSuccess", "Alias modificado exitosamente");
        } catch (Exception e){
            attributes.addFlashAttribute("aliasError", e.getMessage());
        }

        return new RedirectView("/payee/myContactList");
    }
}
