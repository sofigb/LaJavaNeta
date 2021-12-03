package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.repository.ContactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepo contactRepository;

    @Override
    @Transactional
    public void createContact(Contact newContact) {
            newContact.setActive(true);
            contactRepository.save(newContact);
    }

    @Override
    @Transactional
    public void deactivateContact(Integer id) { contactRepository.deleteById(id); }

    @Override
    @Transactional
    public void editContact(Contact updatedContact) {
        if (contactRepository.findById(updatedContact.getId()).isPresent()) {
                contactRepository.save(updatedContact);
        }
    }
}