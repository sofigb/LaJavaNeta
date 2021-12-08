package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.ContactRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class ContactService {

    private final String contact="La información de contacto ";

    @Autowired
    private ContactRepo contactRepository;

    @Transactional
    public void createContact(Contact newContact) throws InputException {
        try {
            checkContact(newContact.getPhoneNumber(), newContact.getEmail());
            newContact.setActive(true);
            contactRepository.save(newContact);
        } catch (Exception e) {
            throw  InputException.NotCreated(contact);
        }
    }

    @Transactional
    public void deactivateContact(Integer id) throws InputException {
        try {
            contactRepository.deleteById(id);
        } catch (Exception e) {
            throw  InputException.NotFound(contact);
        }
    }

    @Transactional
    public void editContact(Contact updatedContact) throws InputException {
        if (contactRepository.findById(updatedContact.getId()).isPresent()) {
            try {
                checkContact(updatedContact.getPhoneNumber(), updatedContact.getEmail());
                contactRepository.save(updatedContact);
            } catch (Exception e) {
                throw InputException.NotEdited(contact);
            }
        } else {
            throw InputException.NotFound(contact);
        }
    }

    public void checkContact(Long phoneNumber, String email) throws InputException, VirtualWalletException {
        Validation.validEmailCheck(email);
        if (contactRepository.existsContactByEmail(email)) {
            throw InputException.UsedEmail(email);
        }
        Validation.validPhoneNumberCheck(phoneNumber);
    }
}