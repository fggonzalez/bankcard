package com.bank.credit_card.persistence.crud;

import com.bank.credit_card.persistence.entity.Card;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CardCrudRepository extends CrudRepository<Card,Long> {
    //Metodo para encontrar las tarjetas Activas

    List<Card> findByIsActive(boolean isActive);
}
