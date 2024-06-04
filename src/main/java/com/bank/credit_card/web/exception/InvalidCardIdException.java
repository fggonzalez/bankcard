
package com.bank.credit_card.web.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidCardIdException extends ResponseStatusException {
    public InvalidCardIdException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
