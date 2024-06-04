package com.bank.credit_card.domain.service.mapper;


import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.persistence.entity.Card;
import com.bank.credit_card.persistence.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CardMapperTest {

    private CardMapper cardMapper;

    @BeforeEach
    void setUp() {
        cardMapper = Mappers.getMapper(CardMapper.class);
    }

    @Test
    void testToCreditCard() {
        Card card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId("123456");
        card.setCardHolderName("John Doe");
        card.setExpirationDate(LocalDate.of(2027, 6, 3));
        card.setActive(true);
        card.setBalance(BigDecimal.valueOf(1000.00));
        card.setTransaction(Collections.emptyList());

        CreditCard creditCard = cardMapper.toCreditCard(card);

        assertEquals(card.getCardId(), creditCard.getIdCard());
        assertEquals(card.getProductId(), creditCard.getIdProduct());
        assertEquals(card.getCardHolderName(), creditCard.getHolderName());
        assertEquals(card.getExpirationDate(), creditCard.getExpiration());
        assertEquals(card.getActive(), creditCard.isActive());
        assertEquals(card.getBalance(), creditCard.getTotalBalance());
        assertEquals(card.getTransaction().size(), creditCard.getTransactionList().size());
    }

    @Test
    void testToCard() {
        CreditCard creditCard = new CreditCard();
        creditCard.setIdCard("1234567890123456");
        creditCard.setIdProduct("123456");
        creditCard.setHolderName("John Doe");
        creditCard.setExpiration(LocalDate.of(2027, 6, 3));
        creditCard.setActive(true);
        creditCard.setTotalBalance(BigDecimal.valueOf(1000.00));
        creditCard.setTransactionList(Collections.emptyList());

        Card card = cardMapper.toCard(creditCard);

        assertEquals(creditCard.getIdCard(), card.getCardId());
        assertEquals(creditCard.getIdProduct(), card.getProductId());
        assertEquals(creditCard.getHolderName(), card.getCardHolderName());
        assertEquals(creditCard.getExpiration(), card.getExpirationDate());
        assertEquals(creditCard.isActive(), card.getActive());
        assertEquals(creditCard.getTotalBalance(), card.getBalance());
        assertEquals(creditCard.getTransactionList().size(), card.getTransaction().size());
    }
}
