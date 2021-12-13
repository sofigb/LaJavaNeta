package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.MyException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
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
    private PayeeRepository payeeRep;
    @Autowired
    CustomerService cservice;

    @Autowired
    private AccountService accountService;

//    @Autowired
//    private CustomerService customerSer;
    private String mensaje = "No existe ningun contacto asociado con el nombre %s";

    @Transactional
    public void create(Payee payee, Integer idCustomer) throws MyException, VirtualWalletException {
        Payee payees=new Payee();
        //VALIDAR FORMATO CUENTA
        //validar que sea letras
        payees.setAccountNumber(payee.getAccountNumber());
        payees.setActive(Boolean.TRUE);
        payees.setName(payee.getName());
        
      
        
        payeeRep.save(payees);
      

    }

    @Transactional
    public void update(Payee payee) throws MyException {
        //  Validar de que el payee existe
        payeeRep.findById(payee.getId()).orElseThrow(() -> new MyException(String.format(mensaje, payee.getId())));

        //validar que sea letras
        Validation.validationName(payee.getName());
        payee.setActive(Boolean.TRUE);
        //VALIDAR FORMATO CUENTA
        payeeRep.save(payee);

    }

    @Transactional(readOnly = true)
    public List<Payee> findAll() {
        return payeeRep.findAll();
    }

    @Transactional(readOnly = true)
    public List<Payee> findActiveOrNot(Boolean status) {
        return payeeRep.findAllByActive(status);
    }

    @Transactional(readOnly = true)
    public Optional<Payee> findById(Integer id) {
        return payeeRep.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Payee> findByCustomerId(Integer id) {
        return payeeRep.findAllByIdCustomer(id);
    }

    @Transactional
    public void active(Integer id) {
        payeeRep.active(id);
    }

    @Transactional
    public void deleteById(Integer id) {
        payeeRep.deleteById(id);
    }
    @Transactional(readOnly = true)
    public Integer idCustomer(Integer id) {
        return payeeRep.idCustomer(id);
    }

    @Transactional(readOnly = true)
    public List<Payee> findByIdAccount(Long idAccount) {

        List<Payee> payeeList = cservice.findById(accountService.findById(idAccount).getCustomer().getId()).get().getPayees();
        return payeeList;
    }

}
