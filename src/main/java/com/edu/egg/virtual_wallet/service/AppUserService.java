package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.AppUser;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface AppUserService {

    void createUser(AppUser newUser) throws VirtualWalletException;
    void deactivateUser(AppUser deletedUser) throws VirtualWalletException;
    void editUser(AppUser updatedUser) throws VirtualWalletException;
    AppUser returnUser(Integer idUser) throws VirtualWalletException;
    // AKA Check Login Details. I think we should use a separate class for Login.
}
