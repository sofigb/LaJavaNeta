package com.edu.egg.virtual_wallet.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "Customers")
@Table(name = "Customers")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Customers c SET c.active = false WHERE c.id = ?")
//@Where(clause = "active = true")
public class Customer {

    @Id
    @Column(name = "idCustomer")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private Long dni;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Name fullName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Contact contactInfo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Address addressInfo;

    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate dateOfBirth;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Login loginInfo;

    @Column(nullable = false)
    private boolean active;

    @CreatedDate
    @Column(columnDefinition = "DATE", nullable = false, updatable = false)
    private LocalDate activationDate;

    @Column(columnDefinition = "DATE")
    private LocalDate deactivationDate;

    @LastModifiedDate
    @Column(columnDefinition = "DATE", nullable = false)
    private LocalDate modificationDate;
    
    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_customer")
    private List<Payee> payees;

    /**
     * ***********************************************************
     ************************** CONSTRUCTOR ***********************
    ************************************************************
     */
    public Customer(Integer id, Long dni, Name fullName, Contact contactInfo, Address addressInfo,
            LocalDate dateOfBirth, Login loginInfo, boolean active, LocalDate activationDate,
            LocalDate deactivationDate, LocalDate modificationDate,List<Payee> payees) {

        this.id = id;
        this.dni = dni;
        this.fullName = fullName;
        this.contactInfo = contactInfo;
        this.addressInfo = addressInfo;
        this.dateOfBirth = dateOfBirth;

        this.loginInfo = loginInfo;
        this.active = active;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
        this.modificationDate = modificationDate;
         this.payees = payees;

    }

    public Customer() {
    }

    public List<Payee> getPayees() {
        return payees;
    }

    public void setPayees(List<Payee> payees) {
        this.payees = payees;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getDni() {
        return dni;
    }

    public void setDni(Long dni) {
        this.dni = dni;
    }

    public Name getFullName() {
        return fullName;
    }

    public void setFullName(Name fullName) {
        this.fullName = fullName;
    }

    public Contact getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(Contact contactInfo) {
        this.contactInfo = contactInfo;
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

    public Login getLoginInfo() {
        return loginInfo;
    }

    public void setLoginInfo(Login loginInfo) {
        this.loginInfo = loginInfo;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public LocalDate getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDate activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDate getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDate deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public LocalDate getModificationDate() {
        return modificationDate;
    }

    public void setModificationDate(LocalDate modificationDate) {
        this.modificationDate = modificationDate;
    }

    /**
     * **********************************************************
     *********************** GETTER AND SETTER *******************
     * **********************************************************
     */
}
