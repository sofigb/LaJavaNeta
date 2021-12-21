
package com.edu.egg.virtual_wallet.controller;
import com.edu.egg.virtual_wallet.entity.*;
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
import org.springframework.web.servlet.support.RequestContextUtils;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

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

    @PostMapping("/save")
    public RedirectView saveChanges(@ModelAttribute("payee") Payee payee) throws InputException {

        pService.update(payee);
        return new RedirectView("/payee");
    }

    @GetMapping("/myContactList")
    public ModelAndView registerr(HttpSession session, HttpServletRequest request) throws InputException {
        ModelAndView mav = new ModelAndView("payee-list");
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        if(flashMap != null){
            mav.addObject("error", flashMap.get("error"));
            mav.addObject("payee", flashMap.get("payee"));
        }else{
            mav.addObject("payee", new Payee());
        }
        mav.addObject("payeeList", pService.findByCustomerIdList(idCustomer));

        return mav;
    }
    @PostMapping("/createPayee")
    public RedirectView saveAliasChanges(@ModelAttribute("payee") Payee payee, HttpSession session, RedirectAttributes attributes) throws Exception {
        Integer idCustomer = cService.findSessionIdCustomer((Integer) session.getAttribute("id"));
        try{
            pService.createDani(payee, idCustomer);
        } catch (Exception e){
            attributes.addFlashAttribute("error", e.getMessage());
            attributes.addFlashAttribute("payee", payee);
            return new RedirectView("/payee/myContactList");
        }
        return new RedirectView("/payee/myContactList");

    }
}
