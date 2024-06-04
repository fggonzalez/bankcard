package com.bank.credit_card.web.controller;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.AnulateTransactionRequest;
import com.bank.credit_card.domain.dto.PurchaseRequest;
import com.bank.credit_card.domain.service.TransactionService;
import com.bank.credit_card.web.exception.InvalidCardIdException;
import com.bank.credit_card.web.exception.InvalidAmountException;
import com.bank.credit_card.web.exception.InvalidTransactionIdException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        } catch (InvalidTransactionIdException e) {
            return ResponseEntity.badRequest().body(null);
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @Operation(summary = "Realizar compra", description = "Realiza una compra con una tarjeta de crédito")
    @ApiResponse(responseCode = "200", description = "Compra realizada exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @PostMapping("/purchase")

        public ResponseEntity<String> purchase(@Valid @RequestBody PurchaseRequest request) {
            try {
                CreditCardTransaction transaction = transactionService.purchase(request.getCardId(), request.getAmount());
                return ResponseEntity.ok("Compra realizada exitosamente, el número de transacción es " + transaction.getIdTransaction());
            } catch (InvalidCardIdException | InvalidAmountException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            } catch (RuntimeException e) {
                return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
            }
        }


        @Operation(summary = "Anular transacción", description = "Anula una transacción de tarjeta de crédito")
    @ApiResponse(responseCode = "200", description = "Transacción anulada exitosamente")
    @ApiResponse(responseCode = "400", description = "Solicitud incorrecta", content = @Content)
    @ApiResponse(responseCode = "500", description = "Error interno del servidor", content = @Content)
    @PostMapping("/anulation")
    public ResponseEntity<String> anulateTransaction(@Valid @RequestBody AnulateTransactionRequest request) {
        try {
            transactionService.anulateTransaction(request.getCardId(), request.getTransactionId());
            return ResponseEntity.ok("Transacción anulada exitosamente"+request.getTransactionId());
        } catch (InvalidCardIdException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(500).body("Error interno del servidor: " + e.getMessage());
        }
    }
}
