package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.NameRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NameServiceImpl implements NameService {

    @Autowired
    private NameRepo nameRepository;

    @Override
    @Transactional
    public void createName(Name newName) throws VirtualWalletException {
        try {
            checkName(newName.getFirstName(), newName.getMiddleName(), newName.getLastName());
            newName.setActive(true);
            nameRepository.save(newName);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deactivateName(Integer id) throws VirtualWalletException {
        try {
            nameRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Name.");
        }
    }

    @Override
    @Transactional
    public void editName(Name updatedName) throws VirtualWalletException {
        if(nameRepository.findById(updatedName.getId()).isPresent()) {
            try {
                checkName(updatedName.getFirstName(), updatedName.getMiddleName(), updatedName.getLastName());
                nameRepository.save(updatedName);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Failed to identify Customers full name");
        }
    }

    @Override
    public void checkName(String firstName, String middleName, String lastName) throws VirtualWalletException{
        Validation.nullCheck(firstName, "First Name");
        Validation.nullCheck(lastName, "Last Name");

        Validation.validNameCheck(firstName, "First Name");
        Validation.validNameCheck(middleName, "Middle Name");
        Validation.validNameCheck(lastName, "Last Name");
    }
}