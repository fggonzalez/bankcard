package com.bank.credit_card.domain.service.mapper;


import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.persistence.entity.Transaction;
import com.bank.credit_card.persistence.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class TransactionMapperTest {

    private TransactionMapper transactionMapper;

    @BeforeEach
    void setUp() {
        transactionMapper = Mappers.getMapper(TransactionMapper.class);
    }

    @Test
    void testToCreditCardTransaction() {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setCardId("1234567890123456");
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setTimestamp(LocalDateTime.of(2024, 6, 3, 20, 0));
        transaction.setAnulled(false);

        CreditCardTransaction creditCardTransaction = transactionMapper.toCreditCardTransaction(transaction);

        assertEquals(transaction.getId(), creditCardTransaction.getIdTransaction());
        assertEquals(transaction.getCardId(), creditCardTransaction.getIdCard());
        assertEquals(transaction.getAmount(), creditCardTransaction.getTotalAmount());
        assertEquals(transaction.getTimestamp(), creditCardTransaction.getTimeTransaction());
        assertEquals(transaction.isAnulled(), creditCardTransaction.isAnulled());
    }

    @Test
    void testToTransaction() {
        CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setIdTransaction(1);
        creditCardTransaction.setIdCard("1234567890123456");
        creditCardTransaction.setTotalAmount(BigDecimal.valueOf(100.00));
        creditCardTransaction.setTimeTransaction(LocalDateTime.of(2024, 6, 3, 20, 0));
        creditCardTransaction.setAnulled(false);

        Transaction transaction = transactionMapper.toTransaction(creditCardTransaction);

        assertEquals(creditCardTransaction.getIdTransaction(), transaction.getId());
        assertEquals(creditCardTransaction.getIdCard(), transaction.getCardId());
        assertEquals(creditCardTransaction.getTotalAmount(), transaction.getAmount());
        assertEquals(creditCardTransaction.getTimeTransaction(), transaction.getTimestamp());
        assertEquals(creditCardTransaction.isAnulled(), transaction.isAnulled());
    }
}
