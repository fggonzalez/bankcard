package com.bank.credit_card.persistence.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "cardid")
    private  String cardId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "timestamp")
    private LocalDateTime timestamp;
    @Column(name = "isanulled")
    private boolean isAnulled;

    @ManyToOne
    @JoinColumn(name = "cardid",insertable = false,updatable = false)
    private Card card;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAnulled() {
        return isAnulled;
    }

    public void setAnulled(boolean anulled) {
        isAnulled = anulled;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardId, amount, timestamp, isAnulled);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", isAnulled=" + isAnulled +
                '}';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return isAnulled == that.isAnulled &&
                Objects.equals(id, that.id) &&
                Objects.equals(cardId, that.cardId) &&
                Objects.equals(amount, that.amount) &&
                Objects.equals(timestamp, that.timestamp);
    }


}