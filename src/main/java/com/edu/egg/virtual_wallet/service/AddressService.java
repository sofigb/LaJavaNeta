package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService{

    @Autowired
    private AddressRepo addressRepository;

    @Transactional
    public void createAddress(Address newAddress) throws VirtualWalletException {
        try {
            addressRepository.save(newAddress);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateAddress(Integer id) throws VirtualWalletException {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Address");
        }
    }

    @Transactional
    public void editAddress(Address updatedAddress) throws VirtualWalletException{
        if (addressRepository.findById(updatedAddress.getId()).isPresent()) {
            try {
                addressRepository.save(updatedAddress);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Failed to identify Customers Address information");
        }
    }
}
