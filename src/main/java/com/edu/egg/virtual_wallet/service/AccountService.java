package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.repository.AccountRepository;
import com.edu.egg.virtual_wallet.utility.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired(required = true)
    private AccountRepository aRepository;

    @Transactional(readOnly = true)
    public List<Account> accountList() {
        return aRepository.findAll();
    }

    @Transactional
    public void createAccount(CurrencyType currency, Customer customer) {

        Account account = new Account();

        // VER CLASE UTILITIES DONDE SE GENERAN
        account.setNumber(createAccountNumber());
        account.setCvu(createAccountCvu());
        account.setAlias(createAccountAlias());
        account.setCurrency(currency);
        account.setBalance(0.0);
        account.setActive(true);
        //account.setActivationDate(LocalDateTime.now());
        account.setCustomer(customer);
        aRepository.save(account);

    }

    @Transactional
    public void updateAlias(String alias, Long id) throws Exception {

        if (aRepository.existsByAlias(alias)) {
            throw new Exception("Ya existe el alias ingresado");
        }

        if (alias.trim().isEmpty() || alias == null) {
            throw new Exception("El alias no puede estar vacío");
        }

        if (alias.length() < 6 || alias.length() > 20) {
            throw new Exception("Tamaño de alias inválido (MIN 6 - MAX 20)");
        }

        Account account = aRepository.findById(id).get();

        account.setAlias(alias);

        aRepository.save(account);
    }

    @Transactional
    public void delete(Long accountNumber) {
        aRepository.deleteById(accountNumber);
    }

    @Transactional
    public void enable(Long accountNumber) {
        aRepository.enableByAccountNumber(accountNumber);
    }

    @Transactional
    public void transaction(Long accountNumber, Double balance) throws Exception {

        Account account = aRepository.findById(accountNumber).get();

        account.setBalance(balance);

        aRepository.save(account);
    }

    @Transactional(readOnly = true)
    public Account findById(Long id) {
        return aRepository.findById(id).get();
    }

    @Transactional(readOnly = true)
    public List<Account> findByCustomerId(Integer id) {
        return aRepository.findAllByIdCustomer(id);
    }

    @Transactional(readOnly = true)
    public void active(Long id) {
        aRepository.active(id);
    }

    @Transactional
    public void deleteById(Long id) {
        aRepository.deleteById(id);
    }

    private Long createAccountNumber() {
        Long newAccountNumber;
        do {
            newAccountNumber = Utility.generateAccountNumber();
        } while (aRepository.existsByNumber(newAccountNumber));

        return newAccountNumber;
    }

    private String createAccountCvu() {
        String newAccountCvu;
        do {
            newAccountCvu = Utility.generateAccountCvu();
        } while (aRepository.existsByCvu(newAccountCvu));

        return newAccountCvu;
    }

    private String createAccountAlias() {
        String newAccountAlias;
        do {
            newAccountAlias = Utility.generateAlias();

        } while (aRepository.existsByAlias(newAccountAlias));

        return newAccountAlias;
    }
}
