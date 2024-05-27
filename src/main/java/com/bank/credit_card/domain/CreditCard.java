package com.bank.credit_card.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCard {
    private String idCard;
    private String idProduct;
    private String holderName;
    private LocalDate expiration;
    private boolean active;
    private  boolean blocked;

    private BigDecimal totalBalance;

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





}