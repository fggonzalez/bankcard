package com.bank.credit_card.persistence;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.domain.repository.TransactionRepo;
import com.bank.credit_card.persistence.crud.TransactionCrudRepository;
import com.bank.credit_card.persistence.entity.Transaction;
import com.bank.credit_card.persistence.mapper.TransactionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TransactionRepository implements TransactionRepo {
    @Autowired
    private TransactionCrudRepository transactionCrudRepository;
    @Autowired
    private TransactionMapper mapper;

    @Override
    public CreditCardTransaction save(CreditCardTransaction creditCardTransaction) {
        // Convertir CreditCardTransaction a entidad Transaction
        Transaction transaction = mapper.toTransaction(creditCardTransaction);

        // Guardar la entidad Transaction en el repositorio
        transaction = transactionCrudRepository.save(transaction);

        // Convertir la entidad guardada de vuelta a CreditCardTransaction
        return mapper.toCreditCardTransaction(transaction);
    }

    @Override
    public Optional<CreditCardTransaction> findById(Integer id) {
        Optional<Transaction> transaction = transactionCrudRepository.findById(String.valueOf(id));
        return transaction.map(t -> mapper.toCreditCardTransaction(t));
    }

    @Override
    public void deleteById(Integer id) {
        transactionCrudRepository.deleteById(String.valueOf(id));
    }


}