package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.repository.TransactionRepository;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private PayeeService payeeService;

    @Autowired(required=true)
    private AccountService accountService;
    
    @Autowired(required=true)
    private TransactionRepository transactionRep;

    @Transactional
    public void create(Transaction transaction) throws MyException, Exception {

        Validation.checkReference(transaction.getReference());
        Validation.notNullNegativeAmout(transaction.getAmount());
        Validation.insufficientBalance(transaction.getSenderAccount().getBalance(), transaction.getAmount());
//        Validation.exitsPayee((payeeService.findById(transaction.getPayee().getId())), transaction.getPayee());
//        Validation.exitsAccount((accountService.findByNumber(transaction.getSenderAccount().getNumber())), transaction.getPayee());

        switch (transaction.getType()) {
            case WIRE_TRANSFER:
                accountService.transaction(transaction.getSenderAccount().getNumber(), (transaction.getSenderAccount().getBalance() - transaction.getAmount()));
                break;

            case DEPOSIT:
                accountService.transaction(transaction.getSenderAccount().getNumber(), (transaction.getSenderAccount().getBalance() + transaction.getAmount()));
                break;

            case CHANGE_CURRENCY:

                break;

        }

        transactionRep.save(transaction);

    }

}
