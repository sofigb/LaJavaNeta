package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private String Customer="El cliente ";

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private AppUserService userService;

    @Transactional
    public void createCustomer(Customer newCustomer) throws InputException {
        try {
            userService.createUser(newCustomer.getUser());
            // createAccounts
            customerRepository.save(newCustomer);
        } catch (Exception e) {
            throw  new InputException NotCreated(Customer);
        }
    }

    @Transactional
    public void deactivateCustomer(Integer idCustomer) throws InputException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                userService.deactivateUser(customerRepository.findById(idCustomer).get().getUser()); // ?
                // deactivate account and payees.
                customerRepository.deleteById(idCustomer);
            } catch (Exception e ) {//preguntar si no se puede sacar eso
                //throw new VirtualWalletException("Unable to delete Customer");repeticion
               throw new InputException NotDeleted(Customer);
            }
        } else {
           // throw new VirtualWalletException("Unable to find Customer");repeticion
            throw new InputException NotFound(Customer);
        }
    }

    // There should be a method for updating Customer personal information. This method should utilize UserService methods.
    // There should be another method for adding or deleting payees, and a different method for adding or deactivating Customers bank accounts
    // Transfers should be made by AccountService

    @Transactional
    public void editCustomer(Customer updatedCustomer) throws InputException{
        if (customerRepository.findById(updatedCustomer.getId()).isPresent()) {
            try {
                userService.editUser(updatedCustomer.getUser());
                customerRepository.save(updatedCustomer);
            } catch (Exception e) {
                //throw new VirtualWalletException(e.getMessage());
                throw new InputException NotEdited(Customer);
            }
        } else {
          //  throw new VirtualWalletException("Unable to find Customer");
            throw  new InputException NotFound(Customer);
        }
    }

    @Transactional
    public Customer returnCustomer(Integer idCustomer) throws InputException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
                customer.setUser(userService.returnUser(customer.getUser().getId()));
                return customer;
            } catch (Exception e) {
                //throw new VirtualWalletException(e.getMessage());
                throw new InputException NotReturned(Customer);//falta hacer
            }
        } else {
            //throw new VirtualWalletException("Unable to retrieve customer data");
            throw new  InputException NotRetrievedData(Customer);
        }
    }
}