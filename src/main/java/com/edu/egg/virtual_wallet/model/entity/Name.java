package com.edu.egg.virtual_wallet.model.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "Names")
@Table(name = "Names")
@SQLDelete(sql = "UPDATE Names n SET n.active = false WHERE n.id = ?")
@Where(clause = "active = true")
public class Name {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String firstName;

    private String middleName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private boolean active;

    /***************************************************************
    ************************** CONSTRUCTOR *************************
    ***************************************************************/

    public Name(String firstName, String middleName, String lastName, boolean active) {
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.active = active;
    }

    public Name() {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
