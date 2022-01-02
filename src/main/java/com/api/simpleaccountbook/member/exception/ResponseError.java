package com.api.simpleaccountbook.member.exception;

import lombok.*;
import org.springframework.validation.FieldError;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter @Setter
public class ResponseError {

    private String field;
    private String message;

    public static ResponseError of(FieldError e) {
        return ResponseError.builder()
                .field(e.getField())
                .message(e.getDefaultMessage())
                .build();
    }
}
