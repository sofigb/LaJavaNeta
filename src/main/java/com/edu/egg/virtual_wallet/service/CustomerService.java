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

    //  Address address, Contact contact,
    //                             Name name, Login login

    @Transactional
    public void editCustomer(Customer updatedCustomer, Integer idCustomer, Address address,  Contact contact, Name name, Login login) throws VirtualWalletException {
        customerRepository.findById(idCustomer).orElseThrow(() -> new VirtualWalletException("EDIT NULL" + updatedCustomer.getId()));

        // updatedCustomer.getId()
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();

                addressService.editAddress(address, customerRepository.findAddressIdByCustomerId(idCustomer));
                nameService.editName(name, customerRepository.findNameIdByCustomerId(idCustomer));
                contactService.editContact(contact, customerRepository.findContactIdByCustomerId(idCustomer));
                loginService.editLogin(login, customerRepository.findLoginIdByCustomerId(idCustomer));
                customer.setDni(updatedCustomer.getDni());
                customer.setDateOfBirth(updatedCustomer.getDateOfBirth());
               customerRepository.save(customer);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    @Transactional(readOnly = true)
    public Customer returnCustomer(Integer idCustomer) throws VirtualWalletException { // THIS IS THE PROBLEM
        customerRepository.findById(idCustomer).orElseThrow(() -> new VirtualWalletException("RETURN NULL" + idCustomer));

        if (customerRepository.findById( idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();
               // customer.setLoginInfo(loginService.returnLogin(customerRepository.findLoginIdByCustomerId(idCustomer)));
             //   customer.setContactInfo(contactService.returnContact(customerRepository.findContactIdByCustomerId(idCustomer)));
              //  customer.setFullName(nameService.returnName(customerRepository.findNameIdByCustomerId(idCustomer)));
             //   customer.setAddressInfo(addressService.returnAddress(customerRepository.findAddressIdByCustomerId(idCustomer)));
                return customer;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve customer data");
        }
    }
}