package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.User;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.UserRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserService  {

    private final String user="el usuario ";

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private ContactService contactService;

    @Transactional
    public void createUser(User newUser) throws InputException {
        try {
            checkUser(newUser.getUsername(), newUser.getPassword(), newUser.getSecurityQuestion());
            nameService.createName(newUser.getFullName());
            contactService.createContact(newUser.getContactInfo());
            newUser.setActive(true);
            userRepository.save(newUser);
        } catch (Exception e) {
            throw InputException.NotCreated(user);
        }
    }

    @Transactional
    public void deactivateUser(User deletedUser) throws InputException{ // ?
        try {
            nameService.deactivateName(deletedUser.getFullName().getId());
            contactService.deactivateContact(deletedUser.getContactInfo().getId());
            userRepository.deleteById(deletedUser.getId());
            deletedUser.setDeactivationDate(LocalDate.now());
            userRepository.save(deletedUser);
        } catch (Exception e) {
            throw InputException.NotFound(user);
        }
    }

    @Transactional
    public void editUser(User updatedUser) throws InputException{
        if (userRepository.findById(updatedUser.getId()).isPresent()) {
            try {
                checkUser(updatedUser.getUsername(), updatedUser.getPassword(), updatedUser.getSecurityQuestion());
                nameService.editName(updatedUser.getFullName());
                contactService.editContact(updatedUser.getContactInfo());
                userRepository.save(updatedUser);
            } catch (Exception e) {
                throw InputException.NotEdited(user);
            }
        } else {
            throw InputException.NotFound(user);
        }
    }

    @Transactional
    public User returnUser(Integer idUser) throws InputException{
        if (userRepository.findById(idUser).isPresent()) {
            try {
                User user = userRepository.findById(idUser).get();
                user.setFullName(user.getFullName());
                user.setContactInfo(user.getContactInfo());
                return user;
            } catch (Exception e) {
                throw InputException.NotReturned(user);
            }
        } else {
            throw InputException.NotRetrievedData(user);
        }
    }

    public void checkUser(String username, String password, String securityQuestion) throws VirtualWalletException {
        // How to verify if username and email is unique?
        Validation.nullCheck(username, "Username");
        Validation.nullCheck(securityQuestion, "Security Question");
        Validation.validPasswordCheck(password);
    }
}