package com.bank.credit_card.domain.repository;

import com.bank.credit_card.domain.CreditCardTransaction;

import java.util.Optional;

public interface TransactionRepo {
    CreditCardTransaction save(CreditCardTransaction creditCardTransaction);
    Optional<CreditCardTransaction> findById(Integer id);
    void deleteById(Integer id);
}
