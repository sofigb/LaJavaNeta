package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.model.entity.*;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private AppUserService userService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public void createCustomer(Customer newCustomer) throws VirtualWalletException {
        try {
            newCustomer.setDateOfBirth(newCustomer.getDateOfBirth());
            newCustomer.setAddressInfo(addressService.createAddress(newCustomer.getAddressInfo()));
            userService.createUser(newCustomer.getUser(), "CUSTOMER");
            // createAccount
            customerRepository.save(newCustomer);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

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

    /******************** NEEDS TESTING *********************/

    @Transactional(readOnly = true)
    public Integer getIdCustomerByLoginId(Integer loginId) throws VirtualWalletException {
        Integer id = customerRepository.findUserIdByLoginId(loginId);
        if (id == null) {
            throw new VirtualWalletException("Unable to retrieve customer by login information");
        }
        return id;
    }

    @Transactional(readOnly = true)
    public Customer returnCustomer(Integer loginId) throws VirtualWalletException {
        Integer idCustomer = getIdCustomerByLoginId(loginId);

        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
                customer.setAddressInfo(customer.getAddressInfo());
                AppUser appUser = customer.getUser();
                appUser.setFullName(appUser.getFullName());
                appUser.setContactInfo(appUser.getContactInfo());
                appUser.setLoginDetails(appUser.getLoginDetails());
                customer.setUser(appUser);
                return customer;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve customer data");
        }
    }
}