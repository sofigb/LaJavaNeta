package com.edu.egg.virtual_wallet.controller;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.entity.TransactionPDFExporter;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.service.AccountService;
import com.edu.egg.virtual_wallet.service.CustomerService;
import com.edu.egg.virtual_wallet.service.PayeeService;
import com.edu.egg.virtual_wallet.service.TransactionService;
import com.lowagie.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
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
        ModelAndView mav = new ModelAndView("registerTransaction");

        mav.addObject("transaction", new Transaction());
        mav.addObject("payeeList", pService.findByIdAccountList(idAccount));
        mav.addObject("action", "create/"+idAccount);
        return mav;
    }

    @GetMapping("/create/{idAccount}")
    public RedirectView create(@ModelAttribute("transaction") Transaction transaction, @PathVariable Long idAccount) throws InputException {
        tService.create(transaction, idAccount);
        return new RedirectView("/myDashboard");
    }
    @GetMapping("/export/pdf")
    public void exportToPDF(HttpServletResponse response) throws DocumentException, IOException {
        response.setContentType("application/pdf");

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=Transactions"+ ".pdf";
        response.setHeader(headerKey, headerValue);

        List<Transaction> listTransaction= tService.obtainTransactions();

        TransactionPDFExporter exporter = new TransactionPDFExporter(listTransaction);
        exporter.export(response);

    }

}
