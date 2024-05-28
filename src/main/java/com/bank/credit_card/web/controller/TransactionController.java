package com.bank.credit_card.web.controller;

import com.bank.credit_card.domain.service.TransactionService;
import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.PurchaseRequest;
import com.bank.credit_card.domain.dto.AnulateTransactionRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/transactions")
@Tag(name = "Transaction Controller", description = "Operaciones relacionadas con las transacciones de tarjetas de crédito")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Operation(summary = "Obtener transacción por ID", description = "Obtiene una transacción de tarjeta de crédito por su ID")
    @ApiResponse(responseCode = "200", description = "Transacción obtenida exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @GetMapping("/{transactionId}")
    public ResponseEntity<CreditCardTransaction> getTransactionById(@PathVariable String transactionId) {
        try {
            Integer transactionIdInt = Integer.valueOf(transactionId);
            CreditCardTransaction transaction = transactionService.getTransaction(transactionIdInt);
            return ResponseEntity.ok(transaction);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }




    @Operation(summary = "Realizar compra", description = "Realiza una compra con una tarjeta de crédito")
    @ApiResponse(responseCode = "200", description = "Transacción obtenida exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @PostMapping("/purchase")
    public ResponseEntity<Void> purchase(@Valid @RequestBody PurchaseRequest request) {
        transactionService.purchase(request.getCardId(), request.getAmount());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Anular transacción", description = "Anula una transacción de tarjeta de crédito")
    @ApiResponse(responseCode = "200", description = "Transacción anulada exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @PostMapping("/anulation")
    public ResponseEntity<Void> anulateTransaction(@Valid @RequestBody AnulateTransactionRequest request) {
        transactionService.anulateTransaction(request.getCardId(), request.getTransactionId());
        return ResponseEntity.ok().build();
    }
}
