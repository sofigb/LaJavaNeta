package com.edu.egg.virtual_wallet.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity(name = "Contacts")
@Table(name = "Contacts")
@SQLDelete(sql = "UPDATE Contacts c SET c.active = false WHERE c.id = ?")
@Where(clause = "active = true")
public class Contact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long phoneNumber;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private boolean active;

    /****************************************************************
    ************************** CONSTRUCTOR **************************
    ****************************************************************/

    public Contact(Long phoneNumber, String email, boolean active) {
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.active = active;
    }

    public Contact() {
    }

    /****************************************************************
    ************************ GETTER AND SETTER **********************
    ****************************************************************/

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
