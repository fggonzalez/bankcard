package com.bank.credit_card.web.exception;

import org.springframework.http.HttpStatus;

public class InvalidFullNameException extends CustomException   {
    public InvalidFullNameException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
