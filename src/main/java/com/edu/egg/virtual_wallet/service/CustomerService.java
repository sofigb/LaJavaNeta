package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Customer;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface CustomerService {

    void createCustomer(Customer newCustomer) throws VirtualWalletException;
    void deactivateCustomer(Integer idCustomer) throws VirtualWalletException;
    void editCustomer(Customer updatedCustomer) throws VirtualWalletException;
    Customer returnCustomer(Integer idCustomer) throws VirtualWalletException;
}
