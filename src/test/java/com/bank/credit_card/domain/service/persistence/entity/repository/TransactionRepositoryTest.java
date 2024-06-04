package com.bank.credit_card.domain.service.persistence.entity.repository;



import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.persistence.TransactionRepository;
import com.bank.credit_card.persistence.crud.TransactionCrudRepository;
import com.bank.credit_card.persistence.entity.Transaction;
import com.bank.credit_card.persistence.mapper.TransactionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionRepositoryTest {

    @Mock
    private TransactionCrudRepository transactionCrudRepository;

    @Mock
    private TransactionMapper mapper;

    @InjectMocks
    private TransactionRepository transactionRepository;

    private CreditCardTransaction creditCardTransaction;
    private Transaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        creditCardTransaction = new CreditCardTransaction();
        creditCardTransaction.setIdTransaction(1);
        creditCardTransaction.setIdCard("1234567890123456");
        creditCardTransaction.setTotalAmount(BigDecimal.valueOf(100.00));
        creditCardTransaction.setTimeTransaction(LocalDateTime.now().withNano(0));
        creditCardTransaction.setAnulled(false);

        transaction = new Transaction();
        transaction.setId(1);
        transaction.setCardId("1234567890123456");
        transaction.setAmount(BigDecimal.valueOf(100.00));
        transaction.setTimestamp(LocalDateTime.now().withNano(0));
        transaction.setAnulled(false);
    }

    @Test
    void testSave() {
        when(mapper.toTransaction(any(CreditCardTransaction.class))).thenReturn(transaction);
        when(transactionCrudRepository.save(any(Transaction.class))).thenReturn(transaction);
        when(mapper.toCreditCardTransaction(any(Transaction.class))).thenReturn(creditCardTransaction);

        CreditCardTransaction savedTransaction = transactionRepository.save(creditCardTransaction);

        assertNotNull(savedTransaction);
        assertEquals(creditCardTransaction.getIdTransaction(), savedTransaction.getIdTransaction());
        verify(transactionCrudRepository, times(1)).save(transaction);
    }

    @Test
    void testFindById() {
        when(transactionCrudRepository.findById(any(String.class))).thenReturn(Optional.of(transaction));
        when(mapper.toCreditCardTransaction(any(Transaction.class))).thenReturn(creditCardTransaction);

        Optional<CreditCardTransaction> foundTransaction = transactionRepository.findById(1);

        assertTrue(foundTransaction.isPresent());
        assertEquals(creditCardTransaction.getIdTransaction(), foundTransaction.get().getIdTransaction());
        verify(transactionCrudRepository, times(1)).findById("1");
    }

    @Test
    void testDeleteById() {
        doNothing().when(transactionCrudRepository).deleteById(any(String.class));

        transactionRepository.deleteById(1);

        verify(transactionCrudRepository, times(1)).deleteById("1");
    }
}
