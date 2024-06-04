package com.bank.credit_card.web.exception;

import org.springframework.http.HttpStatus;

public class InvalidProductIdException extends CustomException {
    public InvalidProductIdException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
