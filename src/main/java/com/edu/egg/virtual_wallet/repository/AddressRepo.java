package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepo extends JpaRepository<Address, Integer> {

}