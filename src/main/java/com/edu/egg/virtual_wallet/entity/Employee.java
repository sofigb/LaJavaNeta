package com.edu.egg.virtual_wallet.entity;

import javax.persistence.*;

@Entity(name = "Employees")
@Table(name = "Employees")
public class Employee {

    // Under Revision

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private AppUser user;

    /***************************************************************
    ************************** CONSTRUCTOR *************************
    ***************************************************************/

    public Employee(Integer id, AppUser user) {
        this.id = id;
        this.user = user;
    }

    public Employee() {
    }

    /***************************************************************
    ************************ GETTER AND SETTER *********************
    ***************************************************************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getUser() {
        return user;
    }

    public void setUser(AppUser user) {
        this.user = user;
    }
}