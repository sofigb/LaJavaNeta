package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.entity.UserRole;
import com.edu.egg.virtual_wallet.exception.InputException;
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

    private final String login="el login del cliente ";

    @Autowired
    private LoginRepo loginRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void createLogin(Login newLogin) throws InputException {
        try {
            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());
            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setActive(true);
            loginRepository.save(newLogin);
        } catch (Exception e) {
            throw InputException.NotCreated(login);
        }
    }

    @Transactional
    public void deactivateLogin(Integer id) throws InputException {
        try {
            loginRepository.deleteById(id);
        } catch (Exception e) {
            throw InputException.NotDeleted(login);
        }
    }

    @Transactional
    public void editLogin(Login updatedLogin) throws InputException {
        if(loginRepository.findById(updatedLogin.getId()).isPresent()) {
            try {
                checkLoginDetails(updatedLogin.getUsername(), updatedLogin.getPassword());
                loginRepository.save(updatedLogin);
            } catch (Exception e) {
                throw InputException.NotEdited(login);
            }
        } else {
            throw InputException.NotFound(login);
        }
    }

    public void checkLoginDetails(String username, String password) throws VirtualWalletException,InputException{
        Validation.nullCheck(username, "Username");

        if (loginRepository.existsLoginByUsername(username)) {
            String user="El usuario "+username;
            //throw new VirtualWalletException("Username '" + username +  "' is already taken");
            throw InputException.RepeatedData(user);
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