package com.bank.credit_card.domain.repository;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.dto.TransactionDTO;
import com.bank.credit_card.persistence.entity.Transaction;

import java.util.Optional;

public interface TransactionRepo {
    CreditCardTransaction save(CreditCardTransaction creditCardTransaction);
    Optional<CreditCardTransaction> findById(Integer id);
    void deleteById(Integer id);
}
