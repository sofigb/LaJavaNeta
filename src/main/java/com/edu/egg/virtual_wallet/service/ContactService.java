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

    private final String contact = "La información de contacto ";

    @Autowired
    private ContactRepo contactRepository;

    @Transactional
    public Contact createContact(Contact newContact) throws InputException {
        try {
            checkContact(newContact.getPhoneNumber(), newContact.getEmail(), true);
            newContact.setActive(true);
            contactRepository.save(newContact);
            return newContact;
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
    public void editContact(Contact updatedContact, Integer idContact) throws InputException {
        if (contactRepository.findById(idContact).isPresent()) {

            try {
                boolean newEmailAddress = !updatedContact.getEmail().equals(contactRepository.getById(idContact).getEmail());
                checkContact(updatedContact.getPhoneNumber(), updatedContact.getEmail(), newEmailAddress);

                updatedContact.setId(idContact);
                contactRepository.save(updatedContact);
            } catch (Exception e) {
                throw InputException.NotEdited(contact);
            }
        } else {
            throw InputException.NotFound(contact);
        }
    }

    public void checkContact(Long phoneNumber, String email, boolean newEmailAddress) throws InputException, VirtualWalletException {
        Validation.validEmailCheck(email);

        String usedEmail="El email " + email;

        if (contactRepository.existsContactByEmail(email) && newEmailAddress) {
            throw InputException.RepeatedData(email);
        }

        Validation.validPhoneNumberCheck(phoneNumber);
    }

    @Transactional(readOnly = true)
    public Contact returnContact(Integer idContact) throws VirtualWalletException {
        if (contactRepository.findById(idContact).isPresent()) {
            try {
                return contactRepository.getById(idContact);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Contact not found");
        }
    }
}
