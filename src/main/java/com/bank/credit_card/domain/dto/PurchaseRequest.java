package com.bank.credit_card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
@Schema(description = "Solicitud para realizar una compra con tarjeta de crédito")
public class PurchaseRequest {

    @NotNull(message = "El ID de la tarjeta no puede estar vacio")
    @Schema(description = "ID de la tarjeta", example = "1234567890123456")
    private String cardId;

    @NotNull(message = "El monto no puede ser nulo")
    @Min(value = 0, message = "El monto debe ser mayor o igual a 0")
    @Schema(description = "Monto de la compra", example = "100.00")
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
