package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AppUserService {

    private final String appUser="el usuario ";

    @Autowired
    private AppUserRepo appUserRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private LoginService loginService;

    @Transactional
    public void createUser(AppUser newUser) throws InputException {
        try {
            nameService.createName(newUser.getFullName());
            loginService.createLogin(newUser.getLoginDetails());
            contactService.createContact(newUser.getContactInfo());
            newUser.setActive(true);
            appUserRepository.save(newUser);
        } catch (Exception e) {
            throw InputException.NotCreated(appUser);
        }
    }

    @Transactional
    public void deactivateUser(AppUser deletedUser) throws InputException{
        try {
            nameService.deactivateName(deletedUser.getFullName().getId());
            loginService.deactivateLogin(deletedUser.getLoginDetails().getId());
            contactService.deactivateContact(deletedUser.getContactInfo().getId());
            appUserRepository.deleteById(deletedUser.getId());
            deletedUser.setDeactivationDate(LocalDate.now());
            appUserRepository.save(deletedUser);
        } catch (Exception e) {
            throw InputException.NotDeleted(appUser);
        }
    }

    @Transactional
    public void editUser(AppUser updatedUser) throws InputException {
        if (appUserRepository.findById(updatedUser.getId()).isPresent()) {
            try {
                nameService.editName(updatedUser.getFullName());
                loginService.editLogin(updatedUser.getLoginDetails());
                contactService.editContact(updatedUser.getContactInfo());
                appUserRepository.save(updatedUser);
            } catch (Exception e) {
                throw InputException.NotEdited(appUser);
            }
        } else {
            throw InputException.NotFound(appUser);
        }
    }

    @Transactional
    public AppUser returnUser(Integer idUser) throws InputException{
        if (appUserRepository.findById(idUser).isPresent()) {
            try {
                AppUser appUser = appUserRepository.findById(idUser).get();
                appUser.setFullName(appUser.getFullName());
                appUser.setContactInfo(appUser.getContactInfo());
                return appUser;
            } catch (Exception e) {
                throw InputException.NotReturned(appUser);
            }
        } else {
            throw InputException.NotRetrievedData(appUser);
        }
    }

}