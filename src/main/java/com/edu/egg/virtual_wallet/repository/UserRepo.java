package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Integer> {
    
}