package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.enums.TransactionType;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.PayeeService;
import com.edu.egg.virtual_wallet.service.TransactionService;
import java.util.List;
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
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService tService;

    @Autowired
    PayeeService pservice;
    @Autowired
    AccountService aservice;
   
    @GetMapping("/{idAccount}")
    public ModelAndView show(@PathVariable Long idAccount) {
        ModelAndView mav = new ModelAndView("tablesTransaction");

        mav.addObject("listT", tService.showAllByAccountId(idAccount));
        
        mav.addObject("href", "register/" + idAccount);
        mav.addObject("title", "Transacciones");
        mav.addObject("title1", "Transacciones");
        return mav;
    }

    @GetMapping("/register/{idAccount}")
    public ModelAndView registrer(@PathVariable Long idAccount) {
        ModelAndView mav = new ModelAndView("registerTransaction");

        mav.addObject("transaction", new Transaction());
        mav.addObject("payeeList", pservice.findByIdAccount(idAccount));
        mav.addObject("action", "create/"+idAccount);

        return mav;
    }

    @GetMapping("/create/{idAccount}")
    public RedirectView create(@ModelAttribute("transaction") Transaction transaction, @PathVariable Long idAccount) throws MyException, Exception {

        tService.create(transaction, idAccount);

        return new RedirectView("/transaction/" + idAccount);

    }

//        
    //NO SE PUEDE HAER UNA MODIFICACION  O ELIMINAR DE TRANSACCION, NI AUNQUE FUESE UN SUOER ADMIN NO?
//        @GetMapping("/update/{id}")
//        public ModelAndView update(@PathVariable Integer id) {
//            ModelAndView mav = new ModelAndView("updatePayee");
//
//            mav.addObject("objeto", pservice.findById(id));
//            mav.addObject("action", "save");
//            mav.addObject("title1", " Cambios en Contacto frecuente");
//
//            return mav;
//        }
//        @PostMapping("/save")
//        public RedirectView saveChanges(@ModelAttribute("payee") Payee payee) throws MyException {
//
//            pservice.update(payee);
//
//            return new RedirectView("/payee");
//
//        }
//    }
//
//        @GetMapping("/delete/{id}")
//        public RedirectView delete(@PathVariable Integer id) {
//
//            pservice.deleteById(id);
//
//            return new RedirectView("/payee");
//
//        }
}
