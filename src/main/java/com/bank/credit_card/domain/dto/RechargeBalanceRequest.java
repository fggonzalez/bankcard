package com.bank.credit_card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "Solicitud para recargar el saldo de una tarjeta")
public class RechargeBalanceRequest {

    @Schema(description = "ID de la tarjeta", example = "1234567890123456")
    private String cardId;

    @Schema(description = "Monto a recargar", example = "100.00")
    private BigDecimal balance;

    // Getters y Setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
