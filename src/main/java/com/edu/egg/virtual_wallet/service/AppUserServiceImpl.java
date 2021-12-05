package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AppUserServiceImpl implements AppUserService {

    // Under Revision

    @Autowired
    private AppUserRepo appUserRepository;

    @Autowired
    private NameService nameService;

    @Autowired
    private ContactService contactService;

    @Autowired
    private LoginService loginService;

    @Override
    @Transactional
    public void createUser(AppUser newUser) throws VirtualWalletException {
        try {
            nameService.createName(newUser.getFullName());
            loginService.createLogin(newUser.getLoginDetails());
            contactService.createContact(newUser.getContactInfo());
            newUser.setActive(true);
            appUserRepository.save(newUser);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Override
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

    @Override
    @Transactional
    public void editUser(AppUser updatedUser) throws VirtualWalletException {
        if (appUserRepository.findById(updatedUser.getId()).isPresent()) {
            try {
                nameService.editName(updatedUser.getFullName());
                loginService.editLogin(updatedUser.getLoginDetails());
                contactService.editContact(updatedUser.getContactInfo());
                appUserRepository.save(updatedUser);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Unable to find Customer");
        }
    }

    @Override
    @Transactional
    public AppUser returnUser(Integer idUser) throws VirtualWalletException{
        if (appUserRepository.findById(idUser).isPresent()) {
            try {
                AppUser appUser = appUserRepository.findById(idUser).get();
                appUser.setFullName(appUser.getFullName());
                appUser.setContactInfo(appUser.getContactInfo());
                return appUser;
            } catch (Exception e) {
                throw new VirtualWalletException("Unable to retrieve user data");
            }
        } else {
            throw new VirtualWalletException("Unable to retrieve user data");
        }
    }
}