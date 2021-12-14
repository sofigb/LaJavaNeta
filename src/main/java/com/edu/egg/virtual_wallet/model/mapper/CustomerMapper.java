package com.edu.egg.virtual_wallet.model.mapper;

import com.edu.egg.virtual_wallet.model.entity.*;
import com.edu.egg.virtual_wallet.model.dto.CustomerDTO;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDTO convertToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = new CustomerDTO();

        customerDTO.setCustomerId(customer.getId());
        customerDTO.setUserId(customer.getUser().getId());

        Name name = customer.getUser().getFullName();
        customerDTO.setNameId(name.getId());
        customerDTO.setFirstName(name.getFirstName());
        customerDTO.setMiddleName(name.getMiddleName().isEmpty() ? "-" : name.getMiddleName());
        customerDTO.setLastName(name.getLastName());

        customerDTO.setDateOfBirth(customer.getDateOfBirth());

        Address address = customer.getAddressInfo();
        customerDTO.setAddressId(address.getId());
        customerDTO.setHouseNumber(address.getHouseNumber());
        customerDTO.setStreet(address.getStreet());
        customerDTO.setPostCode(address.getPostCode());
        customerDTO.setCity(address.getCity());
        customerDTO.setCountry(address.getCountry());

        Contact contact = customer.getUser().getContactInfo();
        customerDTO.setContactId(contact.getId());
        customerDTO.setEmail(contact.getEmail());
        customerDTO.setPhoneNumber(contact.getPhoneNumber());

        Login login = customer.getUser().getLoginDetails();
        customerDTO.setLoginId(login.getId());
        customerDTO.setUsername(login.getUsername());
        customerDTO.setPassword(login.getPassword());

        return customerDTO;
    }

    public Customer convertToCustomer(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        AppUser user = new AppUser();

        customer.setId(customerDTO.getCustomerId());
        user.setId(customerDTO.getUserId());

        Name name = new Name();
        name.setId(customerDTO.getNameId());
        name.setFirstName(customerDTO.getFirstName());
        name.setMiddleName(customerDTO.getMiddleName());
        name.setLastName(customerDTO.getLastName());

        customer.setDateOfBirth(customerDTO.getDateOfBirth());

        Address address = new Address();
        address.setId(customerDTO.getAddressId());
        address.setHouseNumber(customerDTO.getHouseNumber());
        address.setStreet(customerDTO.getStreet());
        address.setPostCode(customerDTO.getPostCode());
        address.setCity(customerDTO.getCity());
        address.setCountry(customerDTO.getCountry());

        Contact contact = new Contact();
        contact.setId(customerDTO.getContactId());
        contact.setEmail(customerDTO.getEmail());
        contact.setPhoneNumber(customerDTO.getPhoneNumber());

        Login login = new Login();
        login.setId(customerDTO.getLoginId());
        login.setUsername(customerDTO.getUsername());
        login.setPassword(customerDTO.getPassword());

        user.setFullName(name);
        user.setContactInfo(contact);
        user.setLoginDetails(login);

        customer.setUser(user);
        customer.setAddressInfo(address);

        return customer;
    }
}
