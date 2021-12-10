package com.edu.egg.virtual_wallet.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Customers")
@Table(name = "Customers")
@EntityListeners(AuditingEntityListener.class) // ?
public class Customer {

    @Id
    @Column(name="idCustomer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(nullable = false)
    private AppUser user;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Address addressInfo;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dateOfBirth;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(/*nullable = false,*/name = "fk_customer")
    private List<Account> accounts;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(/*nullable = false,*/name = "fk_customer")
    private List<Payee> payees;

    /**
     * ***********************************************************
     ************************** CONSTRUCTOR ***********************
    ************************************************************
     */
    public Customer(Integer id, AppUser user, Address addressInfo, LocalDate dateOfBirth, List<Account> accounts, List<Payee> payees) {
        this.id = id;
        this.user = user;
        this.addressInfo = addressInfo;
        this.dateOfBirth = dateOfBirth;
        this.accounts = accounts;
        this.payees = payees;
    }

    public Customer() {
    }

    /**
     * **********************************************************
     *********************** GETTER AND SETTER *******************
    ***********************************************************
     */
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

    public Address getAddressInfo() {
        return addressInfo;
    }

    public void setAddressInfo(Address addressInfo) {
        this.addressInfo = addressInfo;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public List<Payee> getPayees() {
        return payees;
    }

    public void setPayees(List<Payee> payees) {
        this.payees = payees;
    }

}
