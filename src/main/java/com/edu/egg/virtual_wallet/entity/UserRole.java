package com.edu.egg.virtual_wallet.entity;

import javax.persistence.*;

@Entity(name = "UserRoles")
@Table(name = "UserRoles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String role;

    /**************************************************************
    ************************** CONSTRUCTOR ************************
    **************************************************************/

    public UserRole(Integer id, String role) {
        this.id = id;
        this.role = role;
    }

    public UserRole() {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}