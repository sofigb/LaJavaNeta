package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Login;

import com.edu.egg.virtual_wallet.exception.InputException;
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

    private final String login = "el login del cliente ";

    @Autowired
    private LoginRepo loginRepository;
    
    @Autowired
    private UserRoleService userRoleService;
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    public Login createLogin(Login newLogin, String role) throws InputException {
        try {
            if(role.equals("EMPLOYEE")) {
                newLogin.setPassword(PasswordPolicyEnforcer.generatePassword());
                System.out.println("PASSWORD");
                System.out.println(newLogin.getPassword());
            }

            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());

            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            newLogin.setLastLoggedIn(LocalDateTime.now());
            newLogin.setActive(true);
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            loginRepository.save(newLogin);

            return newLogin;
        } catch (Exception e) {
            throw new InputException(e.getMessage());
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
    public void editUsername(String username, Integer idLogin) throws InputException {
        if(loginRepository.findById(idLogin).isPresent()) {
            try {
                Login login = loginRepository.findById(idLogin).get();
                // login.setId(idLogin);
                login.setUsername(username);
                loginRepository.save(login);
            } catch (Exception e) {
                throw InputException.NotEdited(login);
            }
        } else {
            throw InputException.NotFound(login);
        }
    }


    @Transactional
    public void editPassword(Integer idLogin, String currentPassword, String newPassword, String confirmNewPassword) throws InputException {
        if(loginRepository.findById(idLogin).isPresent()) {

            Login login = loginRepository.findById(idLogin).get();

            if (!passwordEncoder.encode(currentPassword).equals(login.getPassword())) {

                if (newPassword.equals(confirmNewPassword)){

                    //Validation.validPasswordCheck(newPassword);
                    PasswordPolicyEnforcer.validatePassword(newPassword);
                    login.setPassword(passwordEncoder.encode(newPassword));
                    loginRepository.save(login);

                } else {
                    throw new InputException("Confirm new password - Passwords no not match!");
                }
            } else {
                throw new InputException("Incorrect Password");
           }
        } else {
            throw new InputException("Failed to identify Customers Login details");
        }
    }

    public void checkLoginDetails(String username, String password) throws InputException{
        Validation.nullCheck(username, "Username");

        if (loginRepository.existsLoginByUsername(username)) {
            String user= "El usuario "+ username;
            //throw new VirtualWalletException("Username '" + username +  "' is already taken");
            throw InputException.RepeatedData(user);
        }

        Validation.validPasswordCheck(password);
    }

    @Transactional(readOnly = true)
    public String returnUsername(Integer idLogin) throws InputException {
        if (loginRepository.findById(idLogin).isPresent()) {
            return loginRepository.getById(idLogin).getUsername();
        } else {
            throw new InputException("Username not found");
        }
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
/*
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
    }*/
}