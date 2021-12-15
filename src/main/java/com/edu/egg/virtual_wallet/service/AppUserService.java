package com.edu.egg.virtual_wallet.service;
import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    public AppUser createUser( Contact contact, Name name,Login login, String role) throws InputException {
        try {
            AppUser appUser=new AppUser();
            appUser.setContactInfo(contactService.createContact(contact));
            appUser.setFullName(nameService.createName(name));
            appUser.setLoginDetails(loginService.createLogin(login , role));
            appUser.setActive(true);
            appUser.setLastLoggedIn(LocalDateTime.now());
            appUserRepository.save(appUser);
            return appUser;
        } catch (Exception e) {
            throw InputException.NotCreated(appUser);
        }
    }

    @Transactional
    public void deactivateUser(AppUser deletedUser) throws InputException {
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
    public AppUser returnUser(Integer idUser) throws InputException {
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

