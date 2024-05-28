package com.bank.credit_card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
@Schema(description = "Solicitud para anular una transacción de tarjeta de crédito")
public class AnulateTransactionRequest {

    @NotNull(message = "El ID de la tarjeta no puede ser nulo")
    @Schema(description = "ID de la tarjeta", example = "1234567890123456")
    private String cardId;

    @NotNull(message = "El ID de la transacción no puede ser nulo")
    @Schema(description = "ID de la transacción", example = "123")
    private Integer transactionId;

    // Getters y Setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
}
