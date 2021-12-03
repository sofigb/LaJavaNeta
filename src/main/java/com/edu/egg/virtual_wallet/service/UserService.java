package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.User;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface UserService {

    void createUser(User newUser) throws VirtualWalletException;
    void deactivateUser(User deletedUser) throws VirtualWalletException;
    void editUser(User updatedUser) throws VirtualWalletException;
    User returnUser(Integer idUser) throws VirtualWalletException;
    void checkUser(String username, String password, String securityQuestion) throws VirtualWalletException;
    // AKA Check Login Details. I think we should use a separate class for Login.
}
