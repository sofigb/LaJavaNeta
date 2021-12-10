package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.LoginRepo;

import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class LoginService implements UserDetailsService {

    @Autowired
    private LoginRepo loginRepository;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Login createLogin(Login newLogin, String role) throws VirtualWalletException {
        try {
            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());
            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setActive(true);
            newLogin.setUsername(newLogin.getUsername());
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            loginRepository.save(newLogin);
            return newLogin;
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Transactional
    public void deactivateLogin(Integer id) throws VirtualWalletException {
        try {
            loginRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Login details.");
        }
    }

    @Transactional
    public void editLogin(Login updatedLogin) throws VirtualWalletException {
        if(loginRepository.findById(updatedLogin.getId()).isPresent()) {
            try {
                checkLoginDetails(updatedLogin.getUsername(), updatedLogin.getPassword());
                loginRepository.save(updatedLogin);
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Failed to identify Customers Login details");
        }
    }

    public void checkLoginDetails(String username, String password) throws VirtualWalletException {
        Validation.nullCheck(username, "Username");

        if (loginRepository.existsLoginByUsername(username)) {
            throw new VirtualWalletException("Username '" + username +  "' is already taken");
        }

        Validation.validPasswordCheck(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login loginDetails =  loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + loginDetails.getRole().getRoleName());

        return new User(loginDetails.getUsername(), loginDetails.getPassword(), Collections.singletonList(grantedAuthority));
    }
}