package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.entity.Login;

import com.edu.egg.virtual_wallet.exception.InputException;

import com.edu.egg.virtual_wallet.entity.UserRole;

import com.edu.egg.virtual_wallet.repository.LoginRepo;



import com.edu.egg.virtual_wallet.utility.PasswordPolicyEnforcer;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.Collections;

@Service
public class LoginService implements UserDetailsService {

    private final String login="el login del cliente ";

    @Autowired
    private LoginRepo loginRepository;
    
    @Autowired
    private UserRoleService userRoleService;
    
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Login createLogin(Login newLogin, String role) throws InputException {

        try {
            if(role.equals("EMPLOYEE")) { newLogin.setPassword(PasswordPolicyEnforcer.generatePassword()); }
            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());
            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            newLogin.setLastLoggedIn(LocalDateTime.now());
            newLogin.setActive(true);
            newLogin.setUsername(newLogin.getUsername());
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            loginRepository.save(newLogin);
            return newLogin;
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
    public void editLogin(Login updatedLogin, Integer idLogin, boolean delete) throws InputException {
        if(loginRepository.findById(idLogin).isPresent()) {

            try {
                checkLoginDetails(updatedLogin.getUsername(), updatedLogin.getPassword());

                Login login = loginRepository.findById(idLogin).get();
                login.setPassword(updatedLogin.getPassword());
                login.setUsername(updatedLogin.getUsername());
                login.setActive(delete);

                loginRepository.save(login);
            } catch (Exception e) {
                throw InputException.NotEdited(login);
            }
        } else {
            throw InputException.NotFound(login);
        }
    }

    public void checkLoginDetails(String username, String password) throws InputException {
        Validation.nullCheck(username, "Username");

        if (loginRepository.existsLoginByUsername(username)) {
            String user="El usuario "+username;
            //throw new VirtualWalletException("Username '" + username +  "' is already taken");
            throw InputException.RepeatedData(user);
        }

//        Validation.validPasswordCheck(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Login loginDetails =  loginRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority("ROLE_" + loginDetails.getRole().getRoleName());

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", loginDetails.getId());

        return new User(loginDetails.getUsername(), loginDetails.getPassword(), Collections.singletonList(grantedAuthority));
    }

    @Transactional(readOnly = true)
    public Login returnLogin(Integer idLogin) throws InputException {
        if (loginRepository.findById(idLogin).isPresent()) {
            try {
                return loginRepository.getById(idLogin); // RETURNS NULL VALUES
            } catch (Exception e) {
                throw new InputException(e.getMessage());
            }
        } else {
            throw new InputException("Login not found");
        }
    }
}