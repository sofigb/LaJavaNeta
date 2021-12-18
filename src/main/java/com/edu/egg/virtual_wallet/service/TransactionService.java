package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Transaction;
import com.edu.egg.virtual_wallet.exception.InputException;
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
    private AccountService aService;

    @Autowired(required = true)
    private TransactionRepository tRepository;

    @Transactional(rollbackFor = Exception.class)
    public void create(Transaction transaction, Long idAccount) throws InputException {
        try {
            Validation.checkReference(transaction.getReference());
            Validation.notNullNegativeAmount(transaction.getAmount());
            Transaction transactions = new Transaction();
            transactions.setReference(transaction.getReference());
            transactions.setTimeStamp(LocalDateTime.now());
            transactions.setSenderAccountNumber(aService.findById(idAccount));
            Validation.insufficientBalance(transactions.getSenderAccount().getBalance(), transaction.getAmount());

            transactions.setAmount(transaction.getAmount());
            transactions.setPayee(transaction.getPayee());
            transactions.setCurrency(transactions.getSenderAccount().getCurrency());
            transactions.setType(transaction.getType());
//        Validation.exitsPayee((payeeService.findById(transaction.getPayee().getId())), transaction.getPayee());
//        Validation.exitsAccount((aService.findByNumber(transaction.getSenderAccount().getNumber())), transaction.getPayee());

            switch (transactions.getType()) {
                case WIRE_TRANSFER:
                    aService.transaction(transactions.getSenderAccount().getId(), (transactions.getSenderAccount().getBalance() - transactions.getAmount()));
                    break;
                case DEPOSIT:
                    aService.transaction(transactions.getSenderAccount().getId(), (transactions.getSenderAccount().getBalance() + transactions.getAmount()));
                    break;
            }
            tRepository.save(transactions);
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Transaction> showAllByAccountId(Long id) {
        return tRepository.findAllByIdAccount(id);
    }

    @Transactional(readOnly = true)
    public List<Transaction> obtainTransactions() {
        return tRepository.findAll();
    }
}
