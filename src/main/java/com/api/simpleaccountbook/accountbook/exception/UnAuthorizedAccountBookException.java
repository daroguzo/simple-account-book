package com.api.simpleaccountbook.accountbook.exception;

public class UnAuthorizedAccountBookException extends RuntimeException{
    public UnAuthorizedAccountBookException(String message) {
        super(message);
    }
}
