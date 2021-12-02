package com.edu.egg.virtual_wallet.entity;

import com.edu.egg.virtual_wallet.enums.TransactionType;
import java.time.LocalDateTime;
import com.edu.egg.virtual_wallet.enums.CurrencyType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
    private Payee payee;
    private Long senderAccountNumber;
    private CurrencyType currency;

    public Transaction() {
    }

    public Transaction(Long id, LocalDateTime timeStamp, String reference, TransactionType type, Double amount, Payee payee, Long senderAccountNumber, CurrencyType currency) {
        this.id = id;
        this.timeStamp = timeStamp;
        this.reference = reference;
        this.type = type;
        this.amount = amount;
        this.payee = payee;
        this.senderAccountNumber = senderAccountNumber;
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

    public Long getSenderAccountNumber() {
        return senderAccountNumber;
    }

    public void setSenderAccountNumber(Long senderAccountNumber) {
        this.senderAccountNumber = senderAccountNumber;
    }

    public CurrencyType getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyType currency) {
        this.currency = currency;
    }

}
