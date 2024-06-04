package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.domain.repository.TransactionRepo;
import com.bank.credit_card.web.exception.*;
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
        validateCardId(cardId);
        validateAmount(amount);

        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();

            // Validar fecha de expiración de la tarjeta
            if (card.getExpiration() == null || card.getExpiration().isBefore(LocalDateTime.now().toLocalDate())) {
                throw new CardExpiredException("La tarjeta ha expirado");
            }

            // Verificar si la tarjeta está activa
            if (!card.isActive()) {
                throw new CardNotActiveException("La tarjeta no está activada");
            }

            // Verificar si la tarjeta está bloqueada
            if (card.isBlocked()) {
                throw new CardBlockedException("La tarjeta está bloqueada");
            }

            // Verificar si la tarjeta tiene saldo suficiente
            if (card.getTotalBalance().compareTo(amount) < 0) {
                throw new InsufficientFundsException("Fondos insuficientes");
            }

            // Realizar la transacción
            card.setTotalBalance(card.getTotalBalance().subtract(amount));
            cardRepo.save(card);

            CreditCardTransaction creditCardTransaction = new CreditCardTransaction();
            creditCardTransaction.setIdCard(cardId);
            creditCardTransaction.setTotalAmount(amount);
            creditCardTransaction.setTimeTransaction(LocalDateTime.now().withNano(0));
            creditCardTransaction.setAnulled(false);
            return transactionRepository.save(creditCardTransaction);
        }
        throw new RuntimeException("Tarjeta no encontrada en el sistema");
    }

    public CreditCardTransaction getTransaction(Integer transactionId) {
        // Validar que el transactionId no esté vacío
        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException("El ID de la transacción no puede estar vacío y debe ser mayor a 0.");
        }
        // Buscar la transacción por ID
        Optional<CreditCardTransaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (!optionalTransaction.isPresent()) {
            throw new RuntimeException("Transacción no encontrada");
        }
        return optionalTransaction.get();
    }

    public CreditCardTransaction anulateTransaction(String cardId, Integer transactionId) {
        validateCardId(cardId);
        if (transactionId == null || transactionId <= 0) {
            throw new InvalidTransactionIdException("El ID de la transacción no puede estar vacío y debe ser mayor a 0.");
        }

        Optional<CreditCardTransaction> optionalTransaction = transactionRepository.findById(transactionId);
        if (optionalTransaction.isEmpty()) {
            throw new TransactionNotFoundException("Transacción no encontrada");
        }


        CreditCardTransaction creditCardTransaction = optionalTransaction.get();

        // Validar si la transacción ya está anulada
        if (creditCardTransaction.isAnulled()) {
            throw new RuntimeException("La transacción ya está anulada");
        }
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
                    throw new RuntimeException("Tarjeta no encontrada en el sistema");
                }
            } else {
                throw new RuntimeException("La transacción no puede ser anulada después de 24 horas");
            }
        } else {
            throw new RuntimeException("La transacción no pertenece a la tarjeta especificada");
        }
    }

    private void validateCardId(String cardId) {
        if (cardId == null || cardId.length() != 16 || !cardId.matches("\\d{16}")) {
            throw new InvalidCardIdException("El número de tarjeta debe ser exactamente 16 dígitos y contener solo números");
        }
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidAmountException("El monto debe ser mayor a 0 y contener solo números");
        }
    }
}
