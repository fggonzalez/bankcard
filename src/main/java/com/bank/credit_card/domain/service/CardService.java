package com.bank.credit_card.domain.service;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.persistence.entity.Card;
import com.bank.credit_card.web.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
        if (cardId==null||cardId.length() != 16){
            throw new InvalidCardIdException("El numero de tarjeta debe ser exactamente 16 digitos");
        }
        if(!cardId.matches("\\d+")){
            throw new InvalidCardIdException("Entrada invalida se deben ingresar solo numeros");
        }
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            return optionalCard.get().getTotalBalance();
        }
        throw new InvalidCardIdException("Tarjeta no econtrada en el sistema");
    }

    /**
     * Recarga el saldo de una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @param amount La cantidad a recargar.
     * @return La tarjeta de crédito con el saldo actualizado.
     */
    public CreditCard rechargeBalance(String cardId, BigDecimal amount) {

        if (cardId==null||cardId.length() != 16){
            throw new InvalidCardIdException("El numero de tarjeta debe ser exactamente 16 digitos");
        }
        if(!cardId.matches("\\d+")){
            throw new InvalidCardIdException("Entrada invalida se deben ingresar solo numeros");
        }
        if (amount==null||amount.compareTo(BigDecimal.ZERO) <= 0){
            throw new InvalidAmountException("El monto debe ser mayor a 0 y contener solo numeros  ");

        }
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            card.setTotalBalance(card.getTotalBalance().add(amount));
            return cardRepo.save(card);
        }
        throw new InvalidCardIdException("Tarjeta no econtrada en el sistema");
    }

    /**
     * Activa una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @return La tarjeta de crédito activada.
     */
    public CreditCard activateCard(String cardId) {

        if (cardId==null||cardId.length() != 16){
            throw new InvalidCardIdException("El numero de tarjeta debe ser exactamente 16 digitos");
        }
        if(!cardId.matches("\\d+")){
            throw new InvalidCardIdException("Entrada invalida se deben ingresar solo numeros");
        }
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            if(card.isActive()){
                throw  new CardAlreadyActiveException("La tarjeta ya se encuentra activada  activa");
            }
            card.setActive(true);
           // cardRepo.save(card);
            return cardRepo.save(card);

        }
        throw new InvalidCardIdException("Tarjeta no econtrada en el sistema");
    }

    /**
     * Bloquea una tarjeta de crédito.
     *
     * @param cardId El ID de la tarjeta de crédito.
     * @return La tarjeta de crédito bloqueada.
     */
    public CreditCard blockCard(String cardId) {
        if (cardId==null||cardId.length() != 16){
            throw new InvalidCardIdException("El numero de tarjeta debe ser exactamente 16 digitos");

        }
        if(!cardId.matches("\\d+")){
            throw new InvalidCardIdException("Entrada invalida se deben ingresar solo numeros");

        }
        Optional<CreditCard> optionalCard = cardRepo.findById(cardId);
        if (optionalCard.isPresent()) {
            CreditCard card = optionalCard.get();
            if(card.isBlocked()){
                throw  new CardAlreadyBlockedException("La tarjeta ya se encuentra bloqueada");
            }
            card.setBlocked(true);
            return cardRepo.save(card);
        }
        throw new RuntimeException("Tarjeta no econtrada en el sistema");
    }

    /**
     * Genera un número de tarjeta basado en el ID del producto proporcionado.
     *
     * @param productId El ID del producto.
     * @param fullName El nombre del cliente .
     * @return El número de tarjeta generado.
     */



    public String generateCardNumber(String productId,String fullName) {
        // Validar que se proporcione un productId de 6 dígitos que sean solo numeros no letras
        if (productId == null || !productId.matches("\\d{6}")) {
            throw new InvalidProductIdException("El id para generar la tarjeta debe ser de 6 digitos exactos");
        }
        // Validar que se proporcione el nombre completo
        if (fullName == null || fullName.trim().isEmpty()) {
            throw new InvalidFullNameException("Debe insertar todo el nombre completo del Cliente   ");
        }

        if (!fullName.matches("[a-zA-Z\\s]+")){
            throw new InvalidFullNameException("Debe insertar un nombre valido ");
        }

        // Separar el primer nombre y el primer apellido
        String[] nameParts = fullName.trim().split("\\s+");
        if (nameParts.length < 2) {
            throw new InvalidFullNameException("Se debe incluir por lo menos el primer nombre y apellido ");
        }
        String firstName = nameParts[0];
        String lastName = nameParts[nameParts.length-2];

        //Generar numeros random
        Random random = new Random();
        StringBuilder cardNumber = new StringBuilder(productId);
        for (int i = 0; i < 10; i++) {
            cardNumber.append(random.nextInt(10));
        }
        // Crear y persistir la tarjeta
        CreditCard card = new CreditCard();
        card.setIdProduct(productId);
        //card.setCardNumber(cardNumber.toString());
        card.setIdCard(cardNumber.toString());
        card.setHolderName(firstName + " " + lastName);
        card.setExpiration(LocalDate.now().plusYears(3));
        card.setActive(false);
        card.setBlocked(false);
        card.setTotalbalance(BigDecimal.ZERO);

        cardRepo.save(card);

        return cardNumber.toString();
    }
}
