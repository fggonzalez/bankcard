package com.bank.credit_card.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CardAlreadyBlockedException extends RuntimeException {

public CardAlreadyBlockedException(String message){
    super(message);

}
}
