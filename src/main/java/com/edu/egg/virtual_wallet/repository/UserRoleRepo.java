package com.edu.egg.virtual_wallet.repository;

import com.edu.egg.virtual_wallet.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRoleRepo extends JpaRepository<UserRole, Integer> {

    Optional<UserRole> findByRoleName(String roleName);
}