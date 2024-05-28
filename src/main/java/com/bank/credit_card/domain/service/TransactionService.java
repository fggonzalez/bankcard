package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.domain.repository.TransactionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepo transactionRepository;

    @Autowired
    private CardRepo cardRepo;

    public CreditCardTransaction purchase(String cardId, BigDecimal amount) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            // Asegúrate de que la tarjeta tiene una fecha de expiración válida
            if (card.getExpiration() == null) {
                throw new RuntimeException("Transaction failed: Card expiration date is not set");
            }

            if (card.isActive() && !card.isBlocked() && card.getTotalBalance().compareTo(amount) >= 0 && card.getExpiration().isAfter(LocalDateTime.now().toLocalDate())) {
                card.setTotalBalance(card.getTotalBalance().subtract(amount));
                cardRepo.save(card);

                CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
                creditCardTransaction.setIdCard(cardId);
                creditCardTransaction.setTotalAmount(amount);
                creditCardTransaction.setTimeTransaction(LocalDateTime.now());
                creditCardTransaction.setAnulled(false);
                return transactionRepository.save(creditCardTransaction);
            }
            throw new RuntimeException("Transaction failed: Insufficient funds, inactive or blocked card, or expired card");
        }
        throw new RuntimeException("Card not found");
    }

    public CreditCardTransaction getTransaction(Integer transactionId) {
        return transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    public CreditCardTransaction anulateTransaction(String cardId, Integer transactionId) {
        CreditCardTransaction creditCardTransaction = transactionRepository.findById(transactionId).orElseThrow(() -> new RuntimeException("Transaction not found"));

        if (creditCardTransaction.getIdCard().equals(cardId)) {
            if (creditCardTransaction.getTimeTransaction().isAfter(LocalDateTime.now().minusHours(24))) {
                creditCardTransaction.setAnulled(true);
                creditCardTransaction = transactionRepository.save(creditCardTransaction);

                Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
                if (optionalCard.isPresent()) {
                    CreditCard card = optionalCard.get();
                    card.setTotalBalance(card.getTotalBalance().add(creditCardTransaction.getTotalAmount()));
                    cardRepo.save(card);
                    return creditCardTransaction;
                } else {
                    throw new RuntimeException("Card not found in the system");
                }
            } else {
                throw new RuntimeException("Transaction cannot be annulled after 24 hours");
            }
        } else {
            throw new RuntimeException("Transaction does not belong to the specified card");
        }
    }
}
