package com.bank.credit_card.domain.repository;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.dto.CardDTO;

import java.util.Optional;

public interface CardRepo {
    CreditCard save(CreditCard creditCard);
    Optional<CreditCard> findById(String id);
    void deleteById(String id);
}
