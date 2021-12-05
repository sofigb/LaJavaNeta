package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private AppUserServiceImpl userService;

    @Override
    @Transactional
    public void createCustomer(Customer newCustomer) throws VirtualWalletException {
        try {
            userService.createUser(newCustomer.getUser());
            // createAccounts
            customerRepository.save(newCustomer);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deactivateCustomer(Integer idCustomer) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                userService.deactivateUser(customerRepository.findById(idCustomer).get().getUser()); // ?
                // deactivate account and payees.
                customerRepository.deleteById(idCustomer);
            } catch (Exception e) {
                throw new VirtualWalletException("Unable to delete Customer");
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    // There should be a method for updating Customer personal information. This method should utilize UserService methods.
    // There should be another method for adding or deleting payees, and a different method for adding or deactivating Customers bank accounts
    // Transfers should be made by AccountService

    @Override
    @Transactional
    public void editCustomer(Customer updatedCustomer) throws VirtualWalletException {
        if (customerRepository.findById(updatedCustomer.getId()).isPresent()) {
            try {
                userService.editUser(updatedCustomer.getUser());
                customerRepository.save(updatedCustomer);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    @Override
    @Transactional
    public Customer returnCustomer(Integer idCustomer) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
                customer.setUser(userService.returnUser(customer.getUser().getId()));
                return customer;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve customer data");
        }
    }
}