package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
import com.edu.egg.virtual_wallet.repository.LoginRepo;
import com.edu.egg.virtual_wallet.validation.Validation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {

    @Autowired
    private LoginRepo loginRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createLogin(Login newLogin) throws VirtualWalletException {
        try {
            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());
            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setActive(true);
            loginRepository.save(newLogin);
        } catch (Exception e) {
            throw new VirtualWalletException(e.getMessage());
        }
    }

    @Override
    @Transactional
    public void deactivateLogin(Integer id) throws VirtualWalletException {
        try {
            loginRepository.deleteById(id);
        } catch (Exception e) {
            throw new VirtualWalletException("Unable to delete Customer. Failed to identify Login details.");
        }
    }

    @Override
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

    @Override
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
        return new User(loginDetails.getUsername(), loginDetails.getPassword(), Collections.emptyList());
    }
}