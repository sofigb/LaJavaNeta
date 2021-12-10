package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.service.PayeeService;
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
@RequestMapping("/payee")
public class PayeeController {

    @Autowired
    PayeeService pservice;

    @GetMapping()
    public ModelAndView show() {
        ModelAndView mav = new ModelAndView("tables");

        mav.addObject("listaOb", pservice.findActiveOrNot(Boolean.TRUE));
        mav.addObject("href1", "payee");
        mav.addObject("href", "register");
        mav.addObject("title", "a Contactos Frecuentes");
        mav.addObject("title1", "Contactos Frecuentes");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView registrer() {
        ModelAndView mav = new ModelAndView("registerPayee");

        mav.addObject("payee", new Payee());
        mav.addObject("action", "create");

        return mav;
    }

    @GetMapping("/update/{id}")
    public ModelAndView update(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView("updatePayee");

        mav.addObject("objeto", pservice.findById(id));
        mav.addObject("action", "save");
        mav.addObject("title1", " Cambios en Contacto frecuente");

        return mav;
    }

    @GetMapping("/active/{id}")
    public RedirectView active(@PathVariable Integer id) {

        pservice.active(id);
        return new RedirectView("/payee");

    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {

        pservice.deleteById(id);

        return new RedirectView("/payee");
 

    }

//    @PostMapping("/create")
//    public RedirectView create(@ModelAttribute("payee") Payee payee) throws MyException {
//
//        pservice.create(payee);
//
//        return new RedirectView("/payee");
//      
//
//    }

    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("payee") Payee payee) throws MyException {

        pservice.update(payee);

        return new RedirectView("/payee");

    }
}
