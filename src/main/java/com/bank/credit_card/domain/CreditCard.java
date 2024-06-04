package com.bank.credit_card.domain;

import com.bank.credit_card.persistence.entity.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreditCard {
    private String idCard;
    private String idProduct;
    private String holderName;
    private LocalDate expiration;
    private boolean active;
    private  boolean blocked;

    private BigDecimal totalBalance;
    private List<Transaction> transactionList;

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public BigDecimal getTotalbalance() {
        return totalBalance;
    }

    public void setTotalbalance(BigDecimal totalbalance) {
        totalBalance = totalbalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }


    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(String idProduct) {
        this.idProduct = idProduct;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public LocalDate getExpiration() {
        return expiration;
    }

    public void setExpiration(LocalDate expiration) {
        this.expiration = expiration;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "CreditCard{" +
                "idCard='" + idCard + '\'' +
                ", idProduct='" + idProduct + '\'' +
                ", holderName='" + holderName + '\'' +
                ", expiration=" + expiration +
                ", active=" + active +
                ", blocked=" + blocked +
                ", totalBalance=" + totalBalance +
                ", transactionList=" + transactionList +
                '}';
    }



}