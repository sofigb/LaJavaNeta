package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AppUserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class AppUserService {
//finish agus
    // Under Revision
    private String appUser="El usuario ";

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
        } catch (InputException e) {
            //throw new VirtualWalletException(e.getMessage());
            throw  InputException NotCreated(appUser);
        }
    }

    @Transactional
    public void deactivateUser(AppUser deletedUser) throws InputException{ // ?
        try {
            nameService.deactivateName(deletedUser.getFullName().getId());
            loginService.deactivateLogin(deletedUser.getLoginDetails().getId());
            contactService.deactivateContact(deletedUser.getContactInfo().getId());
            appUserRepository.deleteById(deletedUser.getId());
            deletedUser.setDeactivationDate(LocalDate.now());
            appUserRepository.save(deletedUser);
        } catch (InputException e) {
           // throw new VirtualWalletException("Unable to delete Customer");se repite
            throw  InputException NotDeleted(appUser);//preguntar si es asi
        }
    }
//DOUBT
    @Transactional
    public void editUser(AppUser updatedUser) throws InputException {
        if (appUserRepository.findById(updatedUser.getId()).isPresent()) {
            try {
                nameService.editName(updatedUser.getFullName());
                loginService.editLogin(updatedUser.getLoginDetails());
                contactService.editContact(updatedUser.getContactInfo());
                appUserRepository.save(updatedUser);
            } catch (InputException e) {
                //throw new VirtualWalletException(e.getMessage());
                throw InputException NotEdited(appUser);
            }
        } else {
           // throw new VirtualWalletException("Unable to find Customer");se repite
            throw  InputException NotFound(appUser);
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
            } catch (InputException e) {
                //throw new VirtualWalletException("Unable to retrieve user data");
                throw  InputException NotRetrievedData(appUser);
            }
        } else {
           // throw new VirtualWalletException("Unable to retrieve user data");
            throw  InputException NotRetrievedData(appUser);
        }
       /*







        */



    }





}