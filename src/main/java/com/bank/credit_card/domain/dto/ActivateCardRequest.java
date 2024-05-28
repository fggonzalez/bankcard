package com.bank.credit_card.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Solicitud para activar una tarjeta")
public class ActivateCardRequest {

    @NotNull(message = "El ID de la tarjeta no puede estar vacío")
    @Schema(description = "ID de la tarjeta", example = "1234567890123456")
    private String cardId;

    // Getters y Setters
    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }
}
