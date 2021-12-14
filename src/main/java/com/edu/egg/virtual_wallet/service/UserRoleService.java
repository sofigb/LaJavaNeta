package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.model.entity.UserRole;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.UserRoleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserRoleService {

    @Autowired
    private UserRoleRepo userRoleRepository;

    @Transactional(readOnly = true)
    public UserRole findUserRoleByRoleName (String roleName) throws VirtualWalletException{
        return userRoleRepository.findByRoleName(roleName).orElseThrow(() -> new VirtualWalletException("No existe el rol '" + roleName + "'"));
    }
}