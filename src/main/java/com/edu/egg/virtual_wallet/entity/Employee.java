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
    private User user;

    /***************************************************************
    ************************** CONSTRUCTOR *************************
    ***************************************************************/

    public Employee(Integer id, User user) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}