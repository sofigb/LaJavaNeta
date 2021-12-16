package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.NameRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NameService {

    @Autowired
    private NameRepo nameRepository;

    @Transactional
    public Name createName(Name newName) throws VirtualWalletException {
        try {
            checkName(newName.getFirstName(), newName.getMiddleName(), newName.getLastName());
            newName.setActive(true);
            nameRepository.save(newName);
            return newName;
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateName(Integer id) throws VirtualWalletException {
        try {
            nameRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Name.");
        }
    }

    @Transactional
    public void editName(Name updatedName, Integer idName) throws VirtualWalletException {
        if(nameRepository.findById(idName).isPresent()) {
            try {
                checkName(updatedName.getFirstName(), updatedName.getMiddleName(), updatedName.getLastName());
                updatedName.setId(idName);
                updatedName.setActive(true);
                nameRepository.save(updatedName);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Failed to identify Customers full name");
        }
    }

    public void checkName(String firstName, String middleName, String lastName) throws VirtualWalletException{
        Validation.nullCheck(firstName, "First Name");
        Validation.nullCheck(lastName, "Last Name");

        Validation.validNameCheck(firstName, "First Name");
        Validation.validNameCheck(middleName, "Middle Name");
        Validation.validNameCheck(lastName, "Last Name");
    }

    @Transactional(readOnly = true)
    public Name returnName(Integer idName) throws VirtualWalletException {
        if (nameRepository.findById(idName).isPresent()) {
            try {
                return nameRepository.getById(idName); // RETURNS NULL VALUES
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Name not found");
        }
    }
}