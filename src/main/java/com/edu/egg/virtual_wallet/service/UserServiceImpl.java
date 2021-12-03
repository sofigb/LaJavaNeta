package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.User;
import com.edu.egg.virtual_wallet.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private NameServiceImpl nameService;

    @Autowired
    private ContactServiceImpl contactService;

    @Override
    @Transactional
    public void createUser(User newUser) {
        nameService.createName(newUser.getFullName());
        contactService.createContact(newUser.getContactInfo());
        newUser.setActive(true);
        userRepository.save(newUser);
    }

    @Override
    @Transactional
    public void deactivateUser(User deletedUser) { // ?
        nameService.deactivateName(deletedUser.getFullName().getId());
        contactService.deactivateContact(deletedUser.getContactInfo().getId());
        userRepository.deleteById(deletedUser.getId());
        deletedUser.setDeactivationDate(LocalDate.now());
        userRepository.save(deletedUser);
    }

    @Override
    @Transactional
    public void editUser(User updatedUser) {
        if (userRepository.findById(updatedUser.getId()).isPresent()) {
            nameService.editName(updatedUser.getFullName());
            contactService.editContact(updatedUser.getContactInfo());
            userRepository.save(updatedUser);
        }
    }

    @Override
    @Transactional
    public User returnUser(Integer idUser) { // If fetch type is not EAGER.
        if (userRepository.findById(idUser).isPresent()) {
            User user = userRepository.findById(idUser).get();
            user.setFullName(user.getFullName());
            user.setContactInfo(user.getContactInfo());
            return user;
        } else {
            return null; // Throw new Exception
        }
    }
}