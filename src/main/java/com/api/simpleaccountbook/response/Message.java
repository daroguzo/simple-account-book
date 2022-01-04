package com.api.simpleaccountbook.response;

import lombok.Builder;
import lombok.Getter;

@Getter @Builder
public class Message {

    private StateEnum status;
    private String message;
    private Object data;

}
