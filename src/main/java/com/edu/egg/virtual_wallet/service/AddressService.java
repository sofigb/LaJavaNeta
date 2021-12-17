package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;

import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressService {

    private String address = "la dirección "; // exception message

    @Autowired
    private AddressRepo addressRepository;

    @Transactional
    public Address createAddress(Address newAddress) throws InputException {
        try {
            newAddress.setActive(true);
            addressRepository.save(newAddress);
            return newAddress;
        } catch (Exception e) {
            throw InputException.NotCreated(address);
        }
    }

    @Transactional
    public void deactivateAddress(Integer id) throws InputException {
        try {
            addressRepository.deleteById(id);
        } catch (Exception e) {
            throw InputException.NotFound(address);
        }
    }

    @Transactional
    public void editAddress(Address updatedAddress, Integer idAddress) throws InputException {
        if (addressRepository.findById(idAddress).isPresent()) {
            try {
                updatedAddress.setId(idAddress);
                addressRepository.save(updatedAddress);
            } catch (Exception e) {
                throw InputException.NotEdited(address);
            }
        } else {
            throw InputException.NotFound(address);
        }
    }

    @Transactional(readOnly = true)
    public Address returnAddress(Integer idAddress) throws VirtualWalletException {
        if (addressRepository.findById(idAddress).isPresent()) {
            try {
                return addressRepository.getById(idAddress); // RETURNS NULL VALUES
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Address not found");
        }
    }
}