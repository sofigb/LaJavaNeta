package com.edu.egg.virtual_wallet.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "LoginInformation")
@Table(name = "LoginInformation")
@SQLDelete(sql = "UPDATE LoginInformation l SET l.active = false WHERE l.id = ?")
@Where(clause = "active = true")
public class Login {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime lastLoggedIn;

    @Column(nullable = false)
    private boolean active;

    @ManyToOne
    /* The application wil have 3 roles: CUSTOMER, EMPLOYEE and ADMIN
    - We will utilize role hierarchy to be able to assign a single role to each user.
    - For example, a user with the ADMIN role will automatically have the authorisations of
     the EMPLOYEE and CUSTOMER roles */
    @JoinColumn(nullable = false)
    private UserRole role;

    /*
    @Column(nullable = false)
    private String securityQuestion;
    */

    /**************************************************************
    ************************** CONSTRUCTOR ************************
    **************************************************************/

    public Login(Integer id, String username, String password, LocalDateTime lastLoggedIn, boolean active, UserRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.lastLoggedIn = lastLoggedIn;
        this.active = active;
        this.role = role;
    }

    public Login() {
    }

    /**************************************************************
    ************************ GETTER AND SETTER ********************
    **************************************************************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public LocalDateTime getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(LocalDateTime lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }
}