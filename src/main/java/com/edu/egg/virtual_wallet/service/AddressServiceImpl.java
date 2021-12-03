package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Address;
import com.edu.egg.virtual_wallet.repository.AddressRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepo addressRepository;

    @Override
    @Transactional
    public void createAddress(Address newAddress) {
        newAddress.setActive(true);
        addressRepository.save(newAddress);
    }

    @Override
    @Transactional
    public void deactivateAddress(Integer id) { addressRepository.deleteById(id); }

    @Override
    @Transactional
    public void editAddress(Address updatedAddress) {
        if (addressRepository.findById(updatedAddress.getId()).isPresent()) {
            addressRepository.save(updatedAddress);
        }
    }
}
