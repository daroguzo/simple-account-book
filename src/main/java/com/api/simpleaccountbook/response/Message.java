package com.api.simpleaccountbook.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Message {

    private int statusCode;
    private StateEnum code;
    private String message;
    private Object data;

}
