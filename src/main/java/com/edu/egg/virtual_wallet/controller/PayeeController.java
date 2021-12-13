package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.PayeeService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    CustomerService cservice;
   

    @GetMapping("/{idCustomer}")
    public ModelAndView show(@PathVariable Integer idCustomer) {
        ModelAndView mav = new ModelAndView("tables");

        mav.addObject("listaOb", pservice.findActiveOrNot(Boolean.TRUE));
        
        mav.addObject("href", "register/"+idCustomer);
        mav.addObject("title", "a Contactos Frecuentes");
        mav.addObject("title1", "Contactos Frecuentes");
        return mav;
    }

    @GetMapping("/register/{idCustomer}")
    public ModelAndView registrer(@PathVariable Integer idCustomer) {
        ModelAndView mav = new ModelAndView("registerPayee");

        mav.addObject("payee", new Payee());
        mav.addObject("action", "create/"+idCustomer);

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
        return new RedirectView("/payee"+pservice.idCustomer(id));

    }

    @GetMapping("/delete/{id}")
    public RedirectView delete(@PathVariable Integer id) {

        pservice.deleteById(id);
        

        return new RedirectView("/payee/"+pservice.idCustomer(id));

    }

    @PostMapping("/create/{idCustomer}")
    public RedirectView create(@ModelAttribute("payee") Payee payee,@PathVariable Integer idCustomer) throws MyException, VirtualWalletException {

        pservice.create(payee, idCustomer);

        return new RedirectView("/payee/"+idCustomer);
      

    }
    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("payee") Payee payee) throws MyException {

        pservice.update(payee);

        return new RedirectView("/payee");

    }

    
}
