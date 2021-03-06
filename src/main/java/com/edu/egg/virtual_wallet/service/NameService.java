package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.repository.NameRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NameService {

    private final String name = "el nombre ";

    @Autowired
    private NameRepo nameRepository;

    @Transactional(rollbackFor = Exception.class)
    public Name createName(Name newName) throws InputException {
        try {
            checkName(newName.getFirstName(), newName.getMiddleName(), newName.getLastName());
            newName.setActive(true);
            nameRepository.save(newName);
            return newName;
        } catch (Exception e) {
            throw new InputException(e.getMessage());
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void deactivateName(Integer id) throws InputException {
        try {
            nameRepository.deleteById(id);
        } catch (Exception e) {
            throw InputException.NotFound(name);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void editName(Name updatedName, Integer idName) throws InputException {
        if(nameRepository.findById(idName).isPresent()) {
            try {
                checkName(updatedName.getFirstName(), updatedName.getMiddleName(), updatedName.getLastName());
                updatedName.setId(idName);
                nameRepository.save(updatedName);
            } catch (Exception e) {
                throw InputException.NotEdited(name);
            }
        } else {
            throw InputException.NotFound(name);
        }
    }

    public void checkName(String firstName, String middleName, String lastName) throws InputException {
        Validation.nullCheck(firstName, "First Name");
        Validation.nullCheck(lastName, "Last Name");

        Validation.validNameCheck(firstName, "First Name");
        if (middleName != null) Validation.validNameCheck(middleName, "Middle Name");
        Validation.validNameCheck(lastName, "Last Name");
    }

    @Transactional(readOnly = true)
    public Name returnName(Integer idName) throws InputException {
        if (nameRepository.findById(idName).isPresent()) {
            try {
                return nameRepository.getById(idName);
            } catch (Exception e) {
                throw new InputException(e.getMessage());
            }
        } else {
            throw new InputException("Name not found");
        }
    }
}