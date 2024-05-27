package com.bank.credit_card.persistence;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.domain.repository.CardRepo;
import com.bank.credit_card.persistence.crud.CardCrudRepository;
import com.bank.credit_card.persistence.entity.Card;
import com.bank.credit_card.persistence.mapper.CardMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public class CardRepository  implements CardRepo {
    @Autowired
    private CardCrudRepository cardCrudRepository;

    @Autowired
    private CardMapper mapper;

    @Override
    public CreditCard save(CreditCard creditCard) {
        // Convertir CreditCard a entidad Card
        Card card = mapper.toCard(creditCard);

        // Guardar la entidad Card en el repositorio
        card = cardCrudRepository.save(card);

        // Convertir la entidad guardada de vuelta a CreditCard
        return mapper.toCreditCard(card);
    }

    @Override
    public Optional<CreditCard> findById(String id) {
        Optional<Card> card = cardCrudRepository.findById(Long.valueOf(id));
        return card.map(mapper::toCreditCard);
    }

    @Override
    public void deleteById(String id) {

    }


}

