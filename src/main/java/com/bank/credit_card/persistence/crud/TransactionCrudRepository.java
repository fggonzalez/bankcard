package com.bank.credit_card.persistence.crud;

import com.bank.credit_card.persistence.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionCrudRepository extends CrudRepository<Transaction,String> {
   // List<Transaction> findByCardId(Long cardId);

   // @Query("SELECT t FROM Transaction t WHERE t.cardId = :cardId AND t.isAnulled = false")
    List<Transaction> findActiveTransactionsByCardId(String cardId);
}
