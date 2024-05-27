package com.bank.credit_card.domain.dto;

import java.math.BigDecimal;

public class PurchaseRequest {
    private String cardId;
    private BigDecimal amount;

    // Getters y Setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
