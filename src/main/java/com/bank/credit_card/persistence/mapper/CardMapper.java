package com.bank.credit_card.persistence.mapper;

import com.bank.credit_card.domain.CreditCard;
import com.bank.credit_card.persistence.entity.Card;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mappings({
            @Mapping(source = "cardId",target = "idCard"),
            @Mapping(source = "productId",target = "idProduct"),
            @Mapping(source = "cardHolderName",target = "holderName"),
            @Mapping(source = "expirationDate",target = "expiration"),
            @Mapping(source = "active",target = "active"),
            @Mapping(source = "balance",target = "totalBalance"),
            @Mapping(source = "transaction", target = "transactionList")
    }

    )
    CreditCard toCreditCard(Card card);
    @InheritInverseConfiguration
    Card toCard(CreditCard toCreditCard);

}
