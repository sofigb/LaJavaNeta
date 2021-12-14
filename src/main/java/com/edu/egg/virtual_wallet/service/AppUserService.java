package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.model.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepo appUserRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private LoginService loginService;

    @Transactional
    public AppUser createUser(AppUser newUser, String role) throws VirtualWalletException {
        try {
            newUser.setFullName(nameService.createName(newUser.getFullName()));
            newUser.setContactInfo(contactService.createContact(newUser.getContactInfo()));
            newUser.setLoginDetails(loginService.createLogin(newUser.getLoginDetails(), role));
            newUser.setActive(true);
            newUser.setLastLoggedIn(LocalDateTime.now()); // SUPER UNDER REVISION
            appUserRepository.save(newUser);
            return newUser;
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateUser(AppUser deletedUser) throws VirtualWalletException{ // ?
        try {
            nameService.deactivateName(deletedUser.getFullName().getId());
            loginService.deactivateLogin(deletedUser.getLoginDetails().getId());
            contactService.deactivateContact(deletedUser.getContactInfo().getId());
            appUserRepository.deleteById(deletedUser.getId());
            deletedUser.setDeactivationDate(LocalDate.now());
            appUserRepository.save(deletedUser);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer");
        }
    }

    @Transactional
    public AppUser editUser(AppUser updatedUser) throws VirtualWalletException {
        if (appUserRepository.findById(updatedUser.getId()).isPresent()) {
            try {
                nameService.editName(updatedUser.getFullName());
                loginService.editLogin(updatedUser.getLoginDetails());
                contactService.editContact(updatedUser.getContactInfo());
                appUserRepository.save(updatedUser);
                return updatedUser;
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    /******************** NEEDS TESTING *********************/

    @Transactional
    public AppUser returnUser(Integer idUser) throws VirtualWalletException{
        if (appUserRepository.findById(idUser).isPresent()) {
            try {
                AppUser appUser = appUserRepository.findById(idUser).get();
                appUser.setFullName(appUser.getFullName());
                appUser.setContactInfo(appUser.getContactInfo());
                appUser.setLoginDetails(appUser.getLoginDetails());
                return appUser;
            } catch (Exception e) {
                throw new VirtualWalletException("Unable to retrieve user data");
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve user data");
        }
    }
}