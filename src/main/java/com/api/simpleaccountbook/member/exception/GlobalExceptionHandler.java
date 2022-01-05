package com.api.simpleaccountbook.member.exception;

import com.api.simpleaccountbook.response.Message;
import com.api.simpleaccountbook.response.StateEnum;
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
        Message message = Message.builder()
                .statusCode(StateEnum.BAD_REQUEST.getStatusCode())
                .code(StateEnum.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }

    // 비밀번호가 일치하지 않는 경우 예외 처리
    @ExceptionHandler(PasswordNotMatchException.class)
    public ResponseEntity<?> PasswordNotMatchExceptionHandler(PasswordNotMatchException e) {
        Message message = Message.builder()
                .statusCode(StateEnum.BAD_REQUEST.getStatusCode())
                .code(StateEnum.BAD_REQUEST)
                .message(e.getMessage())
                .build();
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
