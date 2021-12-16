package com.edu.egg.virtual_wallet.service;

import com.edu.egg.virtual_wallet.entity.Contact;
import com.edu.egg.virtual_wallet.entity.Login;
import com.edu.egg.virtual_wallet.entity.UserRole;
import com.edu.egg.virtual_wallet.exception.VirtualWalletException;
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

    @Autowired
    private LoginRepo loginRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public Login createLogin(Login newLogin, String role) throws VirtualWalletException {
        try {
            if(role.equals("EMPLOYEE")) { newLogin.setPassword(PasswordPolicyEnforcer.generatePassword()); }
            checkLoginDetails(newLogin.getUsername(), newLogin.getPassword());
            newLogin.setPassword(passwordEncoder.encode(newLogin.getPassword()));
            newLogin.setRole(userRoleService.findUserRoleByRoleName(role));
            newLogin.setLastLoggedIn(LocalDateTime.now());
            newLogin.setActive(true);
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
    public void editLogin(Login updatedLogin, Integer idLogin) throws VirtualWalletException {
        if(loginRepository.findById(idLogin).isPresent()) {
            try {
                checkLoginDetails(updatedLogin.getUsername(), updatedLogin.getPassword());

                Login login = loginRepository.findById(idLogin).get();
                login.setPassword(updatedLogin.getPassword());
                login.setUsername(updatedLogin.getUsername());
                //updatedLogin.setId(idLogin);
                //updatedLogin.setActive(true);
                loginRepository.save(login);
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

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attributes.getRequest().getSession(true);

        session.setAttribute("id", loginDetails.getId());

        return new User(loginDetails.getUsername(), loginDetails.getPassword(), Collections.singletonList(grantedAuthority));
    }

    @Transactional(readOnly = true)
    public Login returnLogin(Integer idLogin) throws VirtualWalletException {
        if (loginRepository.findById(idLogin).isPresent()) {
            try {
                return loginRepository.getById(idLogin); // RETURNS NULL VALUES
            } catch (Exception e) {
                throw new VirtualWalletException(e.getMessage());
            }
        } else {
            throw new VirtualWalletException("Login not found");
        }
    }
}