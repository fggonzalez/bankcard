package com.bank.credit_card.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CreditCardTransaction {
    private Integer idTransaction;
    private String idCard;
    private BigDecimal totalAmount;
    private LocalDateTime timeTransaction;
    private boolean anulled;

    public Integer getIdTransaction() {
        return idTransaction;
    }

    public void setIdTransaction(Integer idTransaction) {
        this.idTransaction = idTransaction;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public LocalDateTime getTimeTransaction() {
        return timeTransaction;
    }

    public void setTimeTransaction(LocalDateTime timeTransaction) {
        this.timeTransaction = timeTransaction;
    }

    public boolean isAnulled() {
        return anulled;
    }

    public void setAnulled(boolean anulled) {
        this.anulled = anulled;
    }


}
