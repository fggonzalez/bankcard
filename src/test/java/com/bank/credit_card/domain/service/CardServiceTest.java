package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.web.exception.CardAlreadyActiveException;
import com.bank.credit_card.web.exception.InvalidCardIdException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class CardServiceTest {

    @Mock
    private CardRepo cardRepo;

    @InjectMocks
    private CardService cardService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSave() {
        CreditCard card = new CreditCard();
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);

        CreditCard savedCard = cardService.save(card);

        assertNotNull(savedCard);
        verify(cardRepo, times(1)).save(card);
    }

    @Test
    void testFindById() {
        CreditCard card = new CreditCard();
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        Optional<CreditCard> foundCard = cardService.findById("1234567890123456");

        assertTrue(foundCard.isPresent());
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testDeleteById() {
        doNothing().when(cardRepo).deleteById(anyString());

        cardService.deleteById("1234567890123456");

        verify(cardRepo, times(1)).deleteById("1234567890123456");
    }

    @Test
    void testGetBalance() {
        CreditCard card = new CreditCard();
        card.setTotalBalance(BigDecimal.valueOf(1000));
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));

        BigDecimal balance = cardService.getBalance("1234567890123456");

        assertEquals(BigDecimal.valueOf(1000), balance);
        verify(cardRepo, times(1)).findById("1234567890123456");
    }

    @Test
    void testRechargeBalance() {
        CreditCard card = new CreditCard();
        card.setTotalBalance(BigDecimal.valueOf(1000));
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);

        CreditCard updatedCard = cardService.rechargeBalance("1234567890123456", BigDecimal.valueOf(500));

        assertNotNull(updatedCard);
        assertEquals(BigDecimal.valueOf(1500), updatedCard.getTotalBalance());
        verify(cardRepo, times(1)).findById("1234567890123456");
        verify(cardRepo, times(1)).save(card);
    }

    @Test
    void testActivateCard() {
        CreditCard card = new CreditCard();
        card.setActive(false);
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);

        CreditCard activatedCard = cardService.activateCard("1234567890123456");

        assertNotNull(activatedCard);
        assertTrue(activatedCard.isActive());
        verify(cardRepo, times(1)).findById("1234567890123456");
        verify(cardRepo, times(1)).save(card);
    }

    @Test
    void testBlockCard() {
        CreditCard card = new CreditCard();
        card.setBlocked(false);
        when(cardRepo.findById(anyString())).thenReturn(Optional.of(card));
        when(cardRepo.save(any(CreditCard.class))).thenReturn(card);

        CreditCard blockedCard = cardService.blockCard("1234567890123456");

        assertNotNull(blockedCard);
        assertTrue(blockedCard.isBlocked());
        verify(cardRepo, times(1)).findById("1234567890123456");
        verify(cardRepo, times(1)).save(card);
    }

    @Test
    void testGenerateCardNumber() {
        when(cardRepo.save(any(CreditCard.class))).thenReturn(new CreditCard());

        String cardNumber = cardService.generateCardNumber("123456", "John Doe");

        assertNotNull(cardNumber);
        assertEquals(16, cardNumber.length());
        verify(cardRepo, times(1)).save(any(CreditCard.class));
    }


}
