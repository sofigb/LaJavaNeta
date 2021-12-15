package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.*;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressService addressService;

    @Transactional
    public void createCustomer(Customer newCustomer, Address address, Contact contact,
                               Name name, Login login) throws VirtualWalletException {
        try {
            newCustomer.setAddressInfo(addressService.createAddress(address));
            newCustomer.setFullName(nameService.createName(name));
            newCustomer.setContactInfo(contactService.createContact(contact));
            newCustomer.setLoginInfo(loginService.createLogin(login, "CUSTOMER"));
            newCustomer.setActive(true);
            customerRepository.save(newCustomer);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateCustomer(Integer idCustomer) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
                addressService.deactivateAddress(customer.getAddressInfo().getId());
                nameService.deactivateName(customer.getFullName().getId());
                loginService.deactivateLogin(customer.getLoginInfo().getId());
                contactService.deactivateContact(customer.getContactInfo().getId());
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
    public void editCustomer(Customer updatedCustomer, Address address, Contact contact,
                             Name name, Login login) throws VirtualWalletException {
        if (customerRepository.findById(updatedCustomer.getId()).isPresent()) {
            try {
                addressService.editAddress(address);
                nameService.editName(name);
                contactService.editContact(contact);
                loginService.editLogin(login);
                customerRepository.save(updatedCustomer);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    @Transactional
    public Customer returnCustomer(Integer idCustomer) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
                customer.setLoginInfo(customer.getLoginInfo());
                customer.setContactInfo(customer.getContactInfo());
                customer.setFullName(customer.getFullName());
                customer.setAddressInfo(customer.getAddressInfo());
                return customer;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve customer data");
        }
    }
}