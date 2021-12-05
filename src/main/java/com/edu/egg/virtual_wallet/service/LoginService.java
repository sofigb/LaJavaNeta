package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface LoginService {

    void createLogin(Login newLogin) throws VirtualWalletException;
    void deactivateLogin(Integer id) throws VirtualWalletException;
    void editLogin(Login updatedLogin) throws VirtualWalletException;
    void checkLoginDetails(String username, String password) throws VirtualWalletException;
}