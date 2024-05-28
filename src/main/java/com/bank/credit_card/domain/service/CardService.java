package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.repository.CardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
public class CardService {

    @Autowired
    private CardRepo cardRepo;

    /**
     * Guarda una tarjeta de crédito en el repositorio.
     *
     * @param creditCard La tarjeta de crédito a guardar.
     * @return La tarjeta de crédito guardada.
     */
    public CreditCard save(CreditCard creditCard) {
        return cardRepo.save(creditCard);
    }

    /**
     * Encuentra una tarjeta de crédito por su ID.
     *
     * @param id El ID de la tarjeta de crédito.
     * @return Un Optional que contiene la tarjeta de crédito si se encuentra.
     */
    public Optional<CreditCard> findById(String id) {
        return cardRepo.findById(id);
    }

    /**
     * Elimina una tarjeta de crédito por su ID.
     *
     * @param id El ID de la tarjeta de crédito a eliminar.
     */
    public void deleteById(String id) {
        cardRepo.deleteById(id);
    }

    /**
     * Obtiene el saldo de una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @return El saldo de la tarjeta de crédito.
     */
    public BigDecimal getBalance(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            return optionalCard.get().getTotalBalance();
        }
        throw new RuntimeException("Card not found in the system");
    }

    /**
     * Recarga el saldo de una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @param amount La cantidad a recargar.
     * @return La tarjeta de crédito con el saldo actualizado.
     */
    public CreditCard rechargeBalance(String cardId, BigDecimal amount) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setTotalBalance(card.getTotalBalance().add(amount));
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found in the system");
    }

    /**
     * Activa una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @return La tarjeta de crédito activada.
     */
    public CreditCard activateCard(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setActive(true);
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found in the system");
    }

    /**
     * Bloquea una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @return La tarjeta de crédito bloqueada.
     */
    public CreditCard blockCard(String cardId) {
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setBlocked(true);
            return cardRepo.save(card);
        }
        throw new RuntimeException("Card not found in the system");
    }

    /**
     * Genera un número de tarjeta basado en el ID del producto proporcionado.
     *
     * @param productId El ID del producto.
     * @return El número de tarjeta generado.
     */
    public String generateCardNumber(String productId) {
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(productId);
        for (int i = 0; i < 10; i++) {
            cardNumber.append(random.nextInt(10));
        }
        return cardNumber.toString();
    }
}
