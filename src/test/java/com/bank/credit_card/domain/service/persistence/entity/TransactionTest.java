package com.bank.credit_card.domain.service.persistence.entity;
import com.bank.credit_card.persistence.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId(1);
        transaction.setCardId("1234567890123456");
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setTimestamp(LocalDateTime.now().withNano(0));
        transaction.setAnulled(false);
    }

    @Test
    void testTransactionCreation() {
        assertEquals(1, transaction.getId());
        assertEquals("1234567890123456", transaction.getCardId());
        assertEquals(BigDecimal.valueOf(100.00), transaction.getAmount());
        assertNotNull(transaction.getTimestamp());
        assertFalse(transaction.isAnulled());
    }

    @Test
    void testTransactionEquality() {
        Transaction anotherTransaction = new Transaction();
        anotherTransaction.setId(1);
        anotherTransaction.setCardId("1234567890123456");
        anotherTransaction.setAmount(BigDecimal.valueOf(100.00));
        anotherTransaction.setTimestamp(transaction.getTimestamp());
        anotherTransaction.setAnulled(false);

        assertEquals(transaction, anotherTransaction);
    }
    @Test
    void testTransactionToString() {
        String expectedString = "Transaction{" +
                "id=1" +
                ", cardId='1234567890123456'" +
                ", amount=100.0" +
                ", timestamp=" + transaction.getTimestamp() +
                ", isAnulled=false" +
                '}';

        assertEquals(expectedString, transaction.toString());
    }




}
