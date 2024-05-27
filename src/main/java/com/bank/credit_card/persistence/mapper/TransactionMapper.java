package com.bank.credit_card.persistence.mapper;

import com.bank.credit_card.domain.CreditCardTransaction;
import com.bank.credit_card.persistence.entity.Transaction;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TransactionMapper {
    @Mappings({
            @Mapping(source = "id", target = "idTransaction"),
            @Mapping(source = "cardId", target = "idCard"),
            @Mapping(source = "amount", target = "totalAmount"),
            @Mapping(source = "timestamp", target = "timeTransaction"),
          //  @Mapping(source = "isAnulled", target = "anulled")
    })
    CreditCardTransaction toCreditCardTransaction(Transaction transaction);
  //  List<CreditCardTransaction>toCreditCardTransaction(List<Transaction>transactions);
    @InheritInverseConfiguration
    Transaction toTransaction(CreditCardTransaction toCreditCardTransaction);
}

