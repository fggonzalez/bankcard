package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.domain.repository.TransactionRepo;
import com.bank.credit_card.web.exception.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class TransactionServiceTest {

    @Mock
    private TransactionRepo transactionRepo;

    @Mock
    private CardRepo cardRepo;

    @InjectMocks
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPurchaseSuccess() {
        CreditCard card = new CreditCard();
        card.setIdCard("1234567890123456");
        card.setExpiration(LocalDateTime.now().plusDays(1).toLocalDate());
        card.setActive(true);
        card.setBlocked(false);
        card.setTotalBalance(BigDecimal.valueOf(1000));

        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);
        when(transactionRepo.save(any(CreditCardTransaction.class))).thenReturn(new CreditCardTransaction());

        CreditCardTransaction transaction = transactionService.purchase("1234567890123456", BigDecimal.valueOf(100));

        assertNotNull(transaction);
        verify(cardRepo, times(1)).findById("1234567890123456");
        verify(cardRepo, times(1)).save(card);
        verify(transactionRepo, times(1)).save(any(CreditCardTransaction.class));
    }

    @Test
    void testPurchaseCardExpired() {
        CreditCard card = new CreditCard();
        card.setExpiration(LocalDateTime.now().minusDays(1).toLocalDate());

        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        assertThrows(CardExpiredException.class, () -> transactionService.purchase("1234567890123456", BigDecimal.valueOf(100)));
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testPurchaseCardNotActive() {
        CreditCard card = new CreditCard();
        card.setExpiration(LocalDateTime.now().plusDays(1).toLocalDate());
        card.setActive(false);

        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        assertThrows(CardNotActiveException.class, () -> transactionService.purchase("1234567890123456", BigDecimal.valueOf(100)));
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testPurchaseCardBlocked() {
        CreditCard card = new CreditCard();
        card.setExpiration(LocalDateTime.now().plusDays(1).toLocalDate());
        card.setActive(true);
        card.setBlocked(true);

        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        assertThrows(CardBlockedException.class, () -> transactionService.purchase("1234567890123456", BigDecimal.valueOf(100)));
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testPurchaseInsufficientFunds() {
        CreditCard card = new CreditCard();
        card.setExpiration(LocalDateTime.now().plusDays(1).toLocalDate());
        card.setActive(true);
        card.setBlocked(false);
        card.setTotalBalance(BigDecimal.valueOf(50));

        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        assertThrows(InsufficientFundsException.class, () -> transactionService.purchase("1234567890123456", BigDecimal.valueOf(100)));
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testGetTransactionSuccess() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.of(transaction));

        CreditCardTransaction result = transactionService.getTransaction(1);

        assertNotNull(result);
        verify(transactionRepo, times(1)).findById(1);
    }

    @Test
    void testGetTransactionNotFound() {
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.getTransaction(1));
        verify(transactionRepo, times(1)).findById(1);
    }

    @Test
    void testAnulateTransactionSuccess() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setIdCard("1234567890123456");
        transaction.setTotalAmount(BigDecimal.valueOf(100));
        transaction.setTimeTransaction(LocalDateTime.now().minusHours(1));
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.of(transaction));
        when(transactionRepo.save(any(CreditCardTransaction.class))).thenReturn(transaction);

        CreditCard card = new CreditCard();
        card.setTotalBalance(BigDecimal.valueOf(500));
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);

        CreditCardTransaction result = transactionService.anulateTransaction("1234567890123456", 1);

        assertNotNull(result);
        assertTrue(result.isAnulled());
        verify(transactionRepo, times(1)).findById(1);
        verify(transactionRepo, times(1)).save(transaction);
        verify(cardRepo, times(1)).findById("1234567890123456");
        verify(cardRepo, times(1)).save(card);
    }

    @Test
    void testAnulateTransactionTransactionNotFound() {
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(TransactionNotFoundException.class, () -> transactionService.anulateTransaction("1234567890123456", 1));
        verify(transactionRepo, times(1)).findById(1);
    }

    @Test
    void testAnulateTransactionTransactionAlreadyAnulled() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setIdCard("1234567890123456");
        transaction.setAnulled(true);
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.of(transaction));

        assertThrows(RuntimeException.class, () -> transactionService.anulateTransaction("1234567890123456", 1));
        verify(transactionRepo, times(1)).findById(1);
    }

    @Test
    void testAnulateTransactionTransactionTooOld() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setIdCard("1234567890123456");
        transaction.setTimeTransaction(LocalDateTime.now().minusDays(2));
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.of(transaction));

        assertThrows(RuntimeException.class, () -> transactionService.anulateTransaction("1234567890123456", 1));
        verify(transactionRepo, times(1)).findById(1);
    }

    @Test
    void testAnulateTransactionCardNotFound() {
        CreditCardTransaction transaction = new CreditCardTransaction();
        transaction.setIdCard("1234567890123456");
        transaction.setTimeTransaction(LocalDateTime.now().minusHours(1));
        when(transactionRepo.findById(anyInt())).thenReturn(Optional.of(transaction));

        when(cardRepo.findById(anyString())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.anulateTransaction("1234567890123456", 1));
        verify(transactionRepo, times(1)).findById(1);
        verify(cardRepo, times(1)).findById("1234567890123456");
    }
}
