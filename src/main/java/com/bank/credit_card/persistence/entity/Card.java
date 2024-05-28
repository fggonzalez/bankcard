package com.bank.credit_card.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name="card")
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cardid", length = 16)
     private String cardId;
    @Column(name = "productid", length = 6, nullable = false)
    private String productId;
    @Column(name = "cardholdername", length = 100, nullable = false)
    private String cardHolderName;
    @Column(name = "expirationdate", nullable = false)
    private LocalDateTime expirationDate;
    @Column(name = "isactive", nullable = false)
    private Boolean isActive;
    @Column(name = "isblocked", nullable = false)
    private  Boolean isBlocked;
    @Column(name = "balance", nullable = false)
    private BigDecimal balance;

    @OneToMany(mappedBy = "card")
    private List<Transaction> transaction;



    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardId, card.cardId) && Objects.equals(productId, card.productId) && Objects.equals(cardHolderName, card.cardHolderName) && Objects.equals(expirationDate, card.expirationDate) && Objects.equals(isActive, card.isActive) && Objects.equals(isBlocked, card.isBlocked) && Objects.equals(balance, card.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, productId, cardHolderName, expirationDate, isActive, isBlocked, balance);
    }

    public String getCardHolderName() {
        return cardHolderName;
    }

    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<Transaction> getTransaction() {
        return transaction;
    }

    public void setTransaction(List<Transaction> transaction) {
        this.transaction = transaction;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getBlocked() {
        return isBlocked;
    }

    public void setBlocked(Boolean blocked) {
        isBlocked = blocked;
    }

    public BigDecimal getNumeric() {
        return balance;
    }

    public void setNumeric(BigDecimal numeric) {
        this.balance = numeric;
    }



}
