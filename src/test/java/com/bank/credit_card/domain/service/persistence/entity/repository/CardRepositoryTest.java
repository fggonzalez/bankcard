package com.bank.credit_card.domain.service.persistence.entity.repository;



import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.persistence.CardRepository;
import com.bank.credit_card.persistence.crud.CardCrudRepository;
import com.bank.credit_card.persistence.entity.Card;
import com.bank.credit_card.persistence.mapper.CardMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CardRepositoryTest {

    @Mock
    private CardCrudRepository cardCrudRepository;

    @Mock
    private CardMapper mapper;

    @InjectMocks
    private CardRepository cardRepository;

    private CreditCard creditCard;
    private Card card;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        creditCard = new CreditCard();
        creditCard.setIdCard("1234567890123456");
        creditCard.setIdProduct("123456");
        creditCard.setHolderName("John Doe");
        creditCard.setExpiration(LocalDate.of(2027, 6, 3));
        creditCard.setActive(true);
        creditCard.setBlocked(false);
        creditCard.setTotalBalance(BigDecimal.valueOf(1000.00));

        card = new Card();
        card.setCardId("1234567890123456");
        card.setProductId("123456");
        card.setCardHolderName("John Doe");
        card.setExpirationDate(LocalDate.of(2027, 6, 3));
        card.setActive(true);
        card.setBlocked(false);
        card.setBalance(BigDecimal.valueOf(1000.00));
    }

    @Test
    void testSave() {
        when(mapper.toCard(any(CreditCard.class))).thenReturn(card);
        when(cardCrudRepository.save(any(Card.class))).thenReturn(card);
        when(mapper.toCreditCard(any(Card.class))).thenReturn(creditCard);

        CreditCard savedCreditCard = cardRepository.save(creditCard);

        assertNotNull(savedCreditCard);
        assertEquals(creditCard.getIdCard(), savedCreditCard.getIdCard());
        verify(cardCrudRepository, times(1)).save(card);
    }

    @Test
    void testFindById() {
        when(cardCrudRepository.findById(any(Long.class))).thenReturn(Optional.of(card));
        when(mapper.toCreditCard(any(Card.class))).thenReturn(creditCard);

        Optional<CreditCard> foundCreditCard = cardRepository.findById("1234567890123456");

        assertTrue(foundCreditCard.isPresent());
        assertEquals(creditCard.getIdCard(), foundCreditCard.get().getIdCard());
        verify(cardCrudRepository, times(1)).findById(1234567890123456L);
    }

    @Test
    void testDeleteById() {
        doNothing().when(cardCrudRepository).deleteById(any(Long.class));

        cardRepository.deleteById("1234567890123456");

        verify(cardCrudRepository, times(1)).deleteById(1234567890123456L);
    }
}
