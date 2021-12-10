package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.repository.AccountRepository;
import com.edu.egg.virtual_wallet.utility.Utilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired(required = true)
    private AccountRepository repository;

    @Transactional(readOnly = true)
    public List<Account> accountList() {
        return repository.findAll();
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
   //     account.setCustomer(customer);
        repository.save(account);
    }

    private Long createAccountNumber() {
        Long newAccountNumber;
        do {
            newAccountNumber = Utilities.generateAccountNumber();
        } while (repository.existsByNumber(newAccountNumber));

        return newAccountNumber;
    }

    private String createAccountCvu() {
        String newAccountCvu;
        do {
            newAccountCvu = Utilities.generateAccountCvu();
        } while (repository.existsByCvu(newAccountCvu));

        return newAccountCvu;
    }

    private String createAccountAlias() {
        String newAccountAlias;
        do {
            newAccountAlias = Utilities.generateAlias();

        } while (repository.existsByAlias(newAccountAlias));

        return newAccountAlias;
    }

    @Transactional
    public void modifyAccountAliases(String alias, Long accountNumber) throws Exception {

        if (repository.existsByAlias(alias)) {
            throw new Exception("Ya existe el alias ingresado");
        }

        if (alias.trim().isEmpty() || alias == null) {
            throw new Exception("El alias no puede estar vacío");
        }

        if (alias.length() < 6 || alias.length() > 20) {
            throw new Exception("Tamaño de alias inválido (MIN 6 - MAX 20)");
        }

        Account account = repository.findById(accountNumber).get();

        account.setAlias(alias);

        repository.save(account);
    }

    @Transactional
    public void deposit(Long accountNumber, Double amount) throws Exception {

        if (amount < 0.0) {
            throw new Exception("El monto ingresado no corresponde");
        }

        if (!repository.existsByNumber(accountNumber)) {
            throw new Exception("La cuenta no existe");
        }

        Account account = repository.findById(accountNumber).get();

        account.setBalance(account.getBalance() + amount);

        repository.save(account);
    }

    @Transactional
    public void withdraw(Long accountNumber, Double amount) throws Exception {

        if (amount < 0.0) {
            throw new Exception("El monto ingresado no corresponde");
        }

        if (!repository.existsByNumber(accountNumber)) {
            throw new Exception("La cuenta no existe");
        }

        Account account = repository.findById(accountNumber).get();

        if (account.getBalance() < amount) {
            throw new Exception("No posee la cantidad solicitada");
        }

        account.setBalance(account.getBalance() - amount);

        repository.save(account);
    }

    @Transactional
    public void delete(Long accountNumber) {
        repository.deleteById(accountNumber);
    }

    @Transactional
    public void enable(Long accountNumber) {
        repository.enableByAccountNumber(accountNumber);
    }

    @Transactional
    public void transaction(Long accountNumber, Double balance) throws Exception {

        Account account = repository.findById(accountNumber).get();

        account.setBalance(balance);

        repository.save(account);
    }

    @Transactional(readOnly = true)
    public Optional<Account> findByNumber(Long number) {
        return repository.findById(number);
    }

    @Transactional(readOnly = true)
    public List<Account> findByCustomerId(Integer id) {
        return repository.findAllByIdCustomer(id);
    }
}
