package com.bank.credit_card.web.controller;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.AnulateTransactionRequest;
import com.bank.credit_card.domain.dto.PurchaseRequest;
import com.bank.credit_card.domain.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
public class transactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/purchase")
    public ResponseEntity<CreditCardTransaction> purchase(@RequestBody PurchaseRequest request) {
        CreditCardTransaction transaction = transactionService.purchase(request.getCardId(), request.getAmount());
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CreditCardTransaction> getTransactionById(@PathVariable Integer id) {
        CreditCardTransaction transaction = transactionService.getTransaction(id);
        return ResponseEntity.ok(transaction);
    }

    @PostMapping("/anulate")
    public ResponseEntity<CreditCardTransaction> anulateTransaction(@RequestBody AnulateTransactionRequest request) {
        CreditCardTransaction transaction = transactionService.anulateTransaction(request.getCardId(),request.getTransactionId());
        return ResponseEntity.ok(transaction);
    }
}

