package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.entity.TransactionPDFExporter;
import com.edu.egg.virtual_wallet.enums.TransactionType;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.PayeeService;
import com.edu.egg.virtual_wallet.service.TransactionService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    @Autowired
    TransactionService tService;

    @Autowired
    PayeeService pService;

    @Autowired
    AccountService aService;

    @Autowired
    private CustomerService cService;

    @GetMapping("/{idAccount}")
    public ModelAndView show(@PathVariable Long idAccount) throws InputException {
        ModelAndView mav = new ModelAndView("tablesTransaction");

        //   Integer idAcount =aService.findByCustomerId(cService.findSessionIdCustomer((Integer) session.getAttribute("id")));
        mav.addObject("listT", tService.showAllByAccountId(idAccount));
        mav.addObject("href", "register/" + idAccount);
        mav.addObject("title", "Transacciones");
        mav.addObject("title1", "Transacciones");
        return mav;
    }

    @GetMapping("/register/{idAccount}")
    public ModelAndView register(@PathVariable Long idAccount) {
        ModelAndView mav = new ModelAndView("transaction-list");

        mav.addObject("transaction", new Transaction());
        mav.addObject("payeeList", pService.findByIdAccountList(idAccount));
        mav.addObject("action", "create/"+idAccount);
        return mav;
    }

    @PostMapping("/create/{idAccount}")
    public RedirectView create(@ModelAttribute("transaction") Transaction transaction, @PathVariable Long idAccount) throws InputException {
        tService.create(transaction, idAccount , TransactionType.WIRE_TRANSFER);
        return new RedirectView("/myDashboard");
    }
    //verificar con post sino
    @GetMapping("/export/pdf/{idAccount}")
    public void exportToPDF(HttpServletResponse response,@PathVariable Long idAccount) throws DocumentException, IOException, InputException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transacciones"+ ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listTransaction= tService.showAllByAccountId(idAccount);

        TransactionPDFExporter exporter = new TransactionPDFExporter(listTransaction);
        exporter.export(response);

    }
/*
    @PostMapping("/create/deposit/{idAccount}")
    public RedirectView createDep(@ModelAttribute("transaction") Transaction transaction, @PathVariable Long idAccount) throws InputException {
        tService.create(transaction, idAccount , TransactionType.DEPOSIT);
        return new RedirectView("/myDashboard");
    }

    @GetMapping("/register/{idAccount}")
    public ModelAndView registerDep(@PathVariable Long idAccount) {
        ModelAndView mav = new ModelAndView("transaction-list");

        mav.addObject("transaction", new Transaction());
        mav.addObject("payeeList", pService.findByIdAccountList(idAccount));
        mav.addObject("action", "create/"+idAccount);
        return mav;
    }
*/
}
