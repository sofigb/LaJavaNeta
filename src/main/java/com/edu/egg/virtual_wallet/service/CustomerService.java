package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.entity.Payee;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.CustomerRepo;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final String customer = "el cliente ";

    @Autowired
    private CustomerRepo customerRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private EmailSenderService emailService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private AddressService addressService;

//    @Autowired
//    private PayeeService payeeService;
    @Transactional

    public void createCustomer(Customer newCustomer, Address address, Contact contact,
                               Name name, Login login) throws InputException {
        try {
            newCustomer.setAddressInfo(addressService.createAddress(address));
            newCustomer.setFullName(nameService.createName(name));
            newCustomer.setContactInfo(contactService.createContact(contact));
            newCustomer.setLoginInfo(loginService.createLogin(login, "CUSTOMER"));
            newCustomer.setActive(true);

            customerRepository.save(newCustomer);
            // emailService.send(newCustomer.getContactInfo().getEmail());

        } catch (Exception e) {
            throw new InputException(e.getMessage());
            // throw InputException.NotCreated(customer);
        }
    }

    @Transactional
    public void deactivateCustomer(Integer idCustomer) throws InputException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();

                addressService.deactivateAddress(customerRepository.findAddressIdByCustomerId(idCustomer));
                nameService.deactivateName(customerRepository.findNameIdByCustomerId(idCustomer));
                loginService.deactivateLogin(customerRepository.findLoginIdByCustomerId(idCustomer));
                contactService.deactivateContact(customerRepository.findContactIdByCustomerId(idCustomer));
                // deactivate account and payees.
                //customer.setId(idCustomer);

                customerRepository.deleteById(idCustomer);

            } catch (Exception e) {
                throw InputException.NotDeleted(customer);
            }
        } else {
            throw InputException.NotFound(customer);
        }
    }

    // There should be a method for updating Customer personal information. This method should utilize UserService methods.
    // There should be another method for adding or deleting payees, and a different method for adding or deactivating Customers bank accounts
    // Transfers should be made by AccountService

    @Transactional
    public void editCustomer(Customer updatedCustomer, Integer idCustomer, Address address,
                             Contact contact, Name name, String username) throws InputException, VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {

            try {
                Customer customer = customerRepository.findById(idCustomer).get();

                addressService.editAddress(address, customerRepository.findAddressIdByCustomerId(idCustomer));
                nameService.editName(name, customerRepository.findNameIdByCustomerId(idCustomer));
                contactService.editContact(contact, customerRepository.findContactIdByCustomerId(idCustomer));
                loginService.editUsername(username, customerRepository.findLoginIdByCustomerId(idCustomer));

                customer.setDni(updatedCustomer.getDni());
                customer.setDateOfBirth(updatedCustomer.getDateOfBirth());

               customerRepository.save(customer);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw InputException.NotFound(customer);
        }
    }

    @Transactional
    public void editCustomerPassword(Integer idCustomer, String currentPassword, String newPassword, String confirmNewPassword) throws InputException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                loginService.editPassword(customerRepository.findLoginIdByCustomerId(idCustomer), currentPassword, newPassword, confirmNewPassword);
            } catch (Exception e) {
                throw InputException.NotEdited("la contraseña ");
            }
        } else {
            throw InputException.NotFound(customer);
        }
    }

    @Transactional(readOnly = true)
    public Customer returnCustomer(Integer idCustomer) throws InputException {

        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                Customer customer = customerRepository.findById(idCustomer).get();

                customer.getLoginInfo().setUsername(loginService.returnUsername(customerRepository.findLoginIdByCustomerId(idCustomer)));
                customer.setContactInfo(contactService.returnContact(customerRepository.findContactIdByCustomerId(idCustomer)));
                customer.setFullName(nameService.returnName(customerRepository.findNameIdByCustomerId(idCustomer)));
                customer.setAddressInfo(addressService.returnAddress(customerRepository.findAddressIdByCustomerId(idCustomer)));

                return customer;
            } catch (Exception e) {
                throw InputException.NotReturned(customer);
            }
        } else {
            throw InputException.NotRetrievedData(customer);
        }
    }

//    no estoy muy segura que sirva 
    @Transactional
    public void savePayeeinList(Integer idCustomer, Payee payee) throws VirtualWalletException {
        if (customerRepository.findById(idCustomer).isPresent()) {
            try {
                List<Payee> payeelist = ((customerRepository.findById(idCustomer)).get()).getPayees();
                payeelist.add(payee);
                Customer customer = (customerRepository.findById(idCustomer)).get();
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

    @Transactional(readOnly = true)
    public Integer findSessionIdCustomer(Integer idLogin) throws VirtualWalletException {
        return customerRepository.findCustomerIdByLoginId(idLogin)
                .orElseThrow(() -> new VirtualWalletException("Current Customer session not found"));
    }
}
