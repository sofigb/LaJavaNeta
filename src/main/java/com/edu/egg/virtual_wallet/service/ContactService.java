package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;

public interface ContactService {

    void createContact(Contact newContact);
    void deactivateContact(Integer id);
    void editContact(Contact updatedContact);
}
