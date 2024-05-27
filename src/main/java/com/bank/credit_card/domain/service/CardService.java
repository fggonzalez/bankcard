package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.persistence.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {


    @Autowired
    private CardRepo cardRepo;

    public CreditCard save(CreditCard creditCard) {
        return cardRepo.save(creditCard);
    }

    public Optional<CreditCard> findById(String id) {
        return cardRepo.findById(id);
    }

    public void deleteById(String id) {
        cardRepo.deleteById(id);
    }

    public BigDecimal getBalance(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            return optionalCard.get().getTotalBalance();
        }
        throw new RuntimeException("Card not found on System");
    }

    public CreditCard rechargeBalance(String cardId, BigDecimal amount) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setTotalBalance(card.getTotalBalance().add(amount));
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found oon system");
    }

    public CreditCard activateCard(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setActive(true);
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found on System");
    }

    public CreditCard blockCard(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setBlocked(true);
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found System");
    }
}


