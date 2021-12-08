package com.edu.egg.virtual_wallet.entity;

import javax.persistence.*;

@Entity(name = "UserRoles")
@Table(name = "UserRoles")
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String roleName;

    /**************************************************************
    ************************** CONSTRUCTOR ************************
    **************************************************************/

    public UserRole(Integer id, String roleName) {
        this.id = id;
        this.roleName = roleName;
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

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}