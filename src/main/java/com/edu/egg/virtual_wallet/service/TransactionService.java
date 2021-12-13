package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.repository.TransactionRepository;
import com.edu.egg.virtual_wallet.validation.Validation;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TransactionService {

    @Autowired
    private PayeeService payeeService;

    @Autowired(required = true)
    private AccountService accountService;

    @Autowired(required = true)
    private TransactionRepository transactionRep;

    @Transactional
    public void create(Transaction transaction, Long idAccount) throws MyException, Exception {

        Validation.checkReference(transaction.getReference());
        Validation.notNullNegativeAmout(transaction.getAmount());
        Transaction transactions = new Transaction();
        transactions.setReference(transaction.getReference());
        transactions.setTimeStamp(LocalDateTime.now());
        transactions.setSenderAccountNumber(accountService.findById(idAccount));
        Validation.insufficientBalance(transactions.getSenderAccount().getBalance(), transaction.getAmount());
        transactions.setAmount(transaction.getAmount());
        transactions.setPayee(transaction.getPayee());
        transactions.setCurrency(transactions.getSenderAccount().getCurrency());
        transactions.setType(transaction.getType());
//        Validation.exitsPayee((payeeService.findById(transaction.getPayee().getId())), transaction.getPayee());
//        Validation.exitsAccount((accountService.findByNumber(transaction.getSenderAccount().getNumber())), transaction.getPayee());

        switch (transactions.getType()) {
            case WIRE_TRANSFER:
                accountService.transaction(transactions.getSenderAccount().getId(), (transactions.getSenderAccount().getBalance() - transactions.getAmount()));
                break;

            case DEPOSIT:
                accountService.transaction(transactions.getSenderAccount().getId(), (transactions.getSenderAccount().getBalance() + transactions.getAmount()));
                break;

            case CHANGE_CURRENCY:

                break;

        }

        transactionRep.save(transactions);

    }

    @Transactional(readOnly = true)
    public List<Transaction> showAllByAccountId(Long id) {
        return transactionRep.findAllByIdAccount(id);
    }

}
