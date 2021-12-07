package com.edu.egg.virtual_wallet.entity;

import com.edu.egg.virtual_wallet.enums.TransactionType;
import java.time.LocalDateTime;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime timeStamp;
    private String reference;
    private TransactionType type;
    private Double amount;
    
    @ManyToOne
    @JoinColumn(nullable = false)
    private Payee payee;
    //NO VA A FUNCIONAR ASI, TENGO QUE TENER UN OBJETO CUENTA
   // private Long senderAccountNumber;
    @ManyToOne
    @JoinColumn(nullable = false)
    private Account senderAccount;
    private CurrencyType currency;

    public Transaction() {
    }

    public Transaction(Long id, LocalDateTime timeStamp, String reference, TransactionType type, Double amount, Payee payee, Account senderAccount, CurrencyType currency) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.reference = reference;
        this.type = type;
        this.amount = amount;
        this.payee = payee;
        this.senderAccount = senderAccount;
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Payee getPayee() {
        return payee;
    }

    public void setPayee(Payee payee) {
        this.payee = payee;
    }

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccountNumber(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

}
