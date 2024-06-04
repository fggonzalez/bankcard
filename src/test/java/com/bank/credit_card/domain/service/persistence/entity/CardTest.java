package com.bank.credit_card.domain.service.persistence.entity;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.persistence.entity.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    private Card card;
    private CreditCard creditCard;

    @BeforeEach
    void setUp() {
        card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId("123456");
        card.setCardHolderName("John Doe");
        card.setExpirationDate(LocalDate.now().plusYears(3));
        card.setActive(true);
        card.setBlocked(false);
        card.setBalance(BigDecimal.valueOf(1000.00));

        creditCard = new CreditCard();
        creditCard.setIdCard(card.getCardId());
        creditCard.setIdProduct(card.getProductId());
        creditCard.setHolderName(card.getCardHolderName());
        creditCard.setExpiration(card.getExpirationDate());
        creditCard.setActive(card.getActive());
        creditCard.setBlocked(card.getBlocked());
        creditCard.setTotalBalance(card.getBalance());
    }

    @Test
    void testCardCreation() {
        assertEquals("1234567890123456", card.getCardId());
        assertEquals("123456", card.getProductId());
        assertEquals("John Doe", card.getCardHolderName());
        assertEquals(LocalDate.now().plusYears(3), card.getExpirationDate());
        assertTrue(card.getActive());
        assertFalse(card.getBlocked());
        assertEquals(BigDecimal.valueOf(1000.00), card.getBalance());
    }

    @Test
    void testCreditCardMapping() {
        assertEquals(card.getCardId(), creditCard.getIdCard());
        assertEquals(card.getProductId(), creditCard.getIdProduct());
        assertEquals(card.getCardHolderName(), creditCard.getHolderName());
        assertEquals(card.getExpirationDate(), creditCard.getExpiration());
        assertEquals(card.getActive(), creditCard.isActive());
        assertEquals(card.getBlocked(), creditCard.isBlocked());
        assertEquals(card.getBalance(), creditCard.getTotalBalance());
    }

    @Test
    void testCardEquality() {
        Card anotherCard = new Card();
        anotherCard.setCardId("1234567890123456");
        anotherCard.setProductId("123456");
        anotherCard.setCardHolderName("John Doe");
        anotherCard.setExpirationDate(LocalDate.now().plusYears(3));
        anotherCard.setActive(true);
        anotherCard.setBlocked(false);
        anotherCard.setBalance(BigDecimal.valueOf(1000.00));

        assertEquals(card, anotherCard);
    }

    @Test
    void testCardHashCode() {
        Card anotherCard = new Card();
        anotherCard.setCardId("1234567890123456");
        anotherCard.setProductId("123456");
        anotherCard.setCardHolderName("John Doe");
        anotherCard.setExpirationDate(LocalDate.now().plusYears(3));
        anotherCard.setActive(true);
        anotherCard.setBlocked(false);
        anotherCard.setBalance(BigDecimal.valueOf(1000.00));

        assertEquals(card.hashCode(), anotherCard.hashCode());
    }





}
