package com.edu.egg.virtual_wallet.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.SQLDelete;

@Entity
@SQLDelete(sql = "UPDATE payee SET active = false WHERE id = ?")
public class Payee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Long accountNumber;
    private String name;
    private Boolean active;
    
//    @ManyToOne
//    @JoinColumn(nullable = false)
//    private Customer customer;

    public Payee() {
    }

    public Payee(Integer id, Long accountNumber, String name, Boolean active, Customer customer) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.name = name;
        this.active = active;
//        this.customer = customer;
    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
