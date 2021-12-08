package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepo extends JpaRepository<Login, Integer> {

    Optional<Login> findByUsername(String username);
    boolean existsLoginByUsername(String username);
}