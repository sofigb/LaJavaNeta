package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.repository.PayeeRepository;
import com.edu.egg.virtual_wallet.validation.Validation;
import java.util.List;
import java.util.Optional;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PayeeService {

    @Autowired
    private PayeeRepository pRepository;
       
    @Autowired
    private CustomerService cService;

    @Autowired
    private AccountService aService;

    private String message = "No existe ningun contacto asociado con el nombre %s";

    @Transactional
    public void create(Payee payee, Integer idCustomer) throws InputException {
        
        try {
            Payee payees = new Payee();
            //VALIDAR FORMATO CUENTA QUE SEAN NUMEROS
            payees.setAccountNumber(payee.getAccountNumber());
            Validation.validationName(payee.getName());
            payees.setName(payee.getName());
            payees.setActive(Boolean.TRUE);
            pRepository.save(payees);
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
    }

    @Transactional
    public void update(Payee payee) throws InputException {
        try {
            pRepository.findById(payee.getId()).orElseThrow(() -> new InputException(String.format(message, payee.getId())));
            Validation.validationName(payee.getName());
            payee.setActive(Boolean.TRUE);
            //VALIDAR FORMATO CUENTA QUE SEAN NUMEROS
            payee.setAccountNumber(payee.getAccountNumber());
            pRepository.save(payee);
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<Payee> findAllList() {
        return pRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Payee> findActiveOrNotList(Boolean status) {
        return pRepository.findAllByActive(status);
    }

    @Transactional(readOnly = true)
    public List<Payee> findByIdAccountList(Long idAccount) {
        return cService.findById(aService.findById(idAccount).getCustomer().getId()).get().getPayees();
    }

    @Transactional(readOnly = true)
    public List<Payee> findByCustomerIdList(Integer id) {
        return pRepository.findAllByIdCustomer(id);
    }
    @Transactional(readOnly = true)
    public Optional<Payee> findById(Integer id) {
        return pRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public Integer idCustomer(Integer id) {
        return pRepository.idCustomer(id);
    }

    @Transactional
    public void active(Integer id) {pRepository.active(id);}

    @Transactional
    public void deleteById(Integer id) {
        pRepository.deleteById(id);
    }
}
