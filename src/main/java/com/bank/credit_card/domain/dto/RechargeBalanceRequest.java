package com.bank.credit_card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Schema(description = "Solicitud para recargar el saldo de una tarjeta")
public class RechargeBalanceRequest {
    @NotNull(message = "El ID de la tarjeta no puede ser nulo")
    @Schema(description = "ID de la tarjeta", example = "1234567890123456")
    private String cardId;
    @NotNull(message = "El monto no puede ser nulo")
    @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0 y contener solo números")
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
