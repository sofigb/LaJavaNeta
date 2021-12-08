package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.NameRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class NameService {
//finish agus
    private String Name="El nombre ";

    @Autowired
    private NameRepo nameRepository;

    @Transactional
    public void createName(Name newName) throws InputException {
        try {
            checkName(newName.getFirstName(), newName.getMiddleName(), newName.getLastName());
            newName.setActive(true);
            nameRepository.save(newName);
        } catch (Exception e) {
            //   throw new VirtualWalletException(e.getMessage());
            throw new InputException NotCreated(Name);
        }
    }

    @Transactional
    public void deactivateName(Integer id) throws InputException {
        try {
            nameRepository.deleteById(id);
        } catch (Exception e) {
            //throw new VirtualWalletException("Unable to delete Customer. Failed to identify Name.");
            throw new InputException NotFound(Name);
        }
    }

    @Transactional
    public void editName(Name updatedName) throws InputException {
        if(nameRepository.findById(updatedName.getId()).isPresent()) {
            try {
                checkName(updatedName.getFirstName(), updatedName.getMiddleName(), updatedName.getLastName());
                nameRepository.save(updatedName);
            } catch (Exception e) {
               // throw new VirtualWalletException(e.getMessage());
                throw new InputException NotEdited(Name);//falta hacer
            }
        } else {
            //throw new VirtualWalletException("Failed to identify Customers full name");
            throw new InputException NotFound(Name);
        }
    }

    public void checkName(String firstName, String middleName, String lastName){
        Validation.nullCheck(firstName, "First Name");
        Validation.nullCheck(lastName, "Last Name");

        Validation.validNameCheck(firstName, "First Name");
        Validation.validNameCheck(middleName, "Middle Name");
        Validation.validNameCheck(lastName, "Last Name");
    }
}