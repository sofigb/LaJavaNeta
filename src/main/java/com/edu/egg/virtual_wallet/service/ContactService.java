package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.ContactRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ContactService {

    @Autowired
    private ContactRepo contactRepository;

    @Transactional
    public void createContact(Contact newContact) throws VirtualWalletException {
        try {
            checkContact(newContact.getPhoneNumber(), newContact.getEmail());
            newContact.setActive(true);
            contactRepository.save(newContact);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateContact(Integer id) throws VirtualWalletException {
        try {
            contactRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Contact Information.");
        }
    }

    @Transactional
    public void editContact(Contact updatedContact) throws VirtualWalletException {
        if (contactRepository.findById(updatedContact.getId()).isPresent()) {
            try {
                checkContact(updatedContact.getPhoneNumber(), updatedContact.getEmail());
                contactRepository.save(updatedContact);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Failed to identify Customers contact information");
        }
    }

    public void checkContact(Long phoneNumber, String email) throws VirtualWalletException {
        Validation.validEmailCheck(email);

        if (contactRepository.existsContactByEmail(email)) {
            throw new VirtualWalletException("Email '" + email +  "' is already taken");
        }

        Validation.validPhoneNumberCheck(phoneNumber);
    }
}