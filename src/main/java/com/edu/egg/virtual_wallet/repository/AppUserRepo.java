package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Integer> {

}