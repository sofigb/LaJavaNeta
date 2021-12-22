package com.edu.egg.virtual_wallet.entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "Employees")
@Table(name = "Employees")
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Employees e SET e.active = false WHERE e.id = ?")
//@Where(clause = "active = true")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)

    private Name fullName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Contact contactInfo;

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

    /***************************************************************
    ************************** CONSTRUCTOR *************************
    ***************************************************************/

    public Employee(Integer id, Name fullName, Contact contactInfo, Login loginInfo, boolean active,
                    LocalDate activationDate, LocalDate deactivationDate, LocalDate modificationDate) {
        this.id = id;
        this.fullName = fullName;
        this.contactInfo = contactInfo;
        this.loginInfo = loginInfo;
        this.active = active;
        this.activationDate = activationDate;
        this.deactivationDate = deactivationDate;
        this.modificationDate = modificationDate;
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
}