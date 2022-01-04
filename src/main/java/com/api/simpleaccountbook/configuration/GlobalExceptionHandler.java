package com.api.simpleaccountbook.configuration;

import com.api.simpleaccountbook.member.exception.PasswordNotMatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 해당되는 사용자가 없는 경우 예외 처리
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<?> UsernameNotFoundExceptionHandler(UsernameNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 비밀번호가 일치하지 않는 경우 예외 처리
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> PasswordNotMatchExceptionHandler(PasswordNotMatchException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 토큰이 만료된 경우
    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<?> TokenExpiredExceptionHandler(TokenExpiredException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // 토큰이 잘못된 경우
    @ExceptionHandler(SignatureVerificationException.class)
    public ResponseEntity<?> SignatureVerificationExceptionHandler(SignatureVerificationException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
