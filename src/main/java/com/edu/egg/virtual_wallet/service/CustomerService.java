package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Account;
import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.entity.Payee;

import com.edu.egg.virtual_wallet.enums.CurrencyType;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
     @Autowired
    private AccountService accountService;
    

//    @Autowired
//    private PayeeService payeeService;
    @Transactional
    public void createCustomer(Customer newCustomer, Address address, Contact contact, Name name,
            Login login) throws VirtualWalletException {
        try {
            String role = "CUSTOMER";
            Customer customer = new Customer();

            //   saveListPayee(customer);
            customer.setDateOfBirth(newCustomer.getDateOfBirth());
            customer.setAddressInfo(addressService.createAddress(address));
            customer.setUser(userService.createUser(contact, name, login, role));
            customerRepository.save(customer);
            accountService.createAccount(CurrencyType.PESO_ARG, customer);
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

//    no estoy muy segura que sirva 
    @Transactional
    public void savePayeeinList(Integer idCustomer,  Payee payee) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                List<Payee> payeelist= ((customerRepository.findById(idCustomer)).get()).getPayees();
                payeelist.add(payee);
                Customer customer=(customerRepository.findById(idCustomer)).get();
                customer.setPayees(payeelist);
                customerRepository.save(customer);

            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }

        }
    }
    @Transactional(readOnly = true)
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);

    }
 

}
