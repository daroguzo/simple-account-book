package com.api.simpleaccountbook.accountbook.exception;

public class AccountBookNotFoundException extends RuntimeException {
    public AccountBookNotFoundException(String message) {
        super(message);
    }
}
