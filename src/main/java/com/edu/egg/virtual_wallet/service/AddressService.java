package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;

public interface AddressService {

    void createAddress(Address newAddress);
    void deactivateAddress(Integer id);
    void editAddress(Address updatedAddress);
    // void checkAddress();
}
