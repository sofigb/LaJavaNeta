package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface ContactService {

    void createContact(Contact newContact) throws VirtualWalletException;
    void deactivateContact(Integer id) throws VirtualWalletException;
    void editContact(Contact updatedContact) throws VirtualWalletException;
    void checkContact(Long phoneNumber, String email) throws VirtualWalletException;
}
