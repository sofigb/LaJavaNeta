package com.edu.egg.virtual_wallet.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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

    @Column(nullable = false)
    private boolean active;

    @ManyToMany
    // Under revision. If we add a Super Admin role, then it will be a ManyToMany relationship, since a Super Admin does everything an Employee can
    // If we only have Customer and Employee roles, then we should map the relationship as ManyToOne
    @JoinColumn(nullable = false)
    private List<UserRole> role;

    /*
    @Column(nullable = false)
    private String securityQuestion;
    */

    /**************************************************************
    ************************** CONSTRUCTOR ************************
    **************************************************************/

    public Login(Integer id, String username, String password, boolean active, List<UserRole> role) {
        this.id = id;
        this.username = username;
        this.password = password;
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

    public List<UserRole> getRole() {
        return role;
    }

    public void setRole(List<UserRole> role) {
        this.role = role;
    }
}