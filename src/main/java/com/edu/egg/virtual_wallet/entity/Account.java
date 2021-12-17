package com.edu.egg.virtual_wallet.entity;

import com.edu.egg.virtual_wallet.enums.CurrencyType;
import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import javax.persistence.OneToMany;

import javax.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@SQLDelete(sql = "UPDATE Account a SET a.active = false WHERE a.id = ?")
public class Account {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    @Column(nullable = false)
    private String cvu;

    @Column(nullable = false)
    private String alias;

    @Column(nullable = false)
    private CurrencyType currency;

    @Column(nullable = false)
    private Double balance;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime activationDate;

    private LocalDateTime deactivationDate;

    private Boolean active;

    @OneToMany
    @JoinColumn()
    private List<Transaction> transactions;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getCvu() {
        return cvu;
    }

    public void setCvu(String cvu) {
        this.cvu = cvu;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getActivationDate() {
        return activationDate;
    }

    public void setActivationDate(LocalDateTime activationDate) {
        this.activationDate = activationDate;
    }

    public LocalDateTime getDeactivationDate() {
        return deactivationDate;
    }

    public void setDeactivationDate(LocalDateTime deactivationDate) {
        this.deactivationDate = deactivationDate;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
