package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.UserRole;
import com.edu.egg.virtual_wallet.exception.InputException;
import com.edu.egg.virtual_wallet.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleService {

    private final String userRole="el rol ";

    @Autowired
    private UserRoleRepo userRoleRepository;
//preguntar elseThrow
    @Transactional(readOnly = true)
    public UserRole findUserRoleByRoleName (String roleName) throws InputException {
        String role=userRole+roleName;
        return userRoleRepository.findByRoleName(roleName).orElseThrow(() -> InputException.NotFound(role));
    }
}