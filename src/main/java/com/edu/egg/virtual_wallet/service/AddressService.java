package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;

public interface AddressService {

    void createAddress(Address newAddress) throws VirtualWalletException;
    void deactivateAddress(Integer id) throws VirtualWalletException;
    void editAddress(Address updatedAddress) throws VirtualWalletException;
    // void checkAddress() throws VirtualWalletException;
}
