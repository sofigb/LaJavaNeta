package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service

public class AddressService {
//finish agus
    @Autowired
    private AddressRepo addressRepository;

    @Transactional
    public void createAddress(Address newAddress) throws InputException {
        try {
            addressRepository.save(newAddress);
        } catch (Exception e) {
            throw new InputException NotCreated();
        }
    }

    @Transactional
    public void deactivateAddress(Integer id) throws InputException {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            //throw new VirtualWalletException("Unable to delete Customer. Failed to identify Address");
                throw  new InputException NotFound();
        }
    }

    @Transactional
    public void editAddress(Address updatedAddress) throws InputException{
        if (addressRepository.findById(updatedAddress.getId()).isPresent()) {
            try {
                addressRepository.save(updatedAddress);
            } catch (Exception e) {
               // throw new VirtualWalletException(e.getMessage());
                throw new InputException NotEdited();
            }
        } else {
           // throw new VirtualWalletException("Failed to identify Customers Address information");
            throw new InputException NotFound();
        }
    }
}
