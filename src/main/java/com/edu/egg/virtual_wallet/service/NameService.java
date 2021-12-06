package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Name;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface NameService {

    void createName(Name newName) throws VirtualWalletException;
    void deactivateName(Integer id) throws VirtualWalletException;
    void editName(Name updatedName) throws VirtualWalletException;
    void checkName(String firstName, String middleName, String lastName) throws VirtualWalletException;
}
