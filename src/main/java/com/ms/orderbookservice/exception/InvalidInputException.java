package com.ms.orderbookservice.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidInputException extends RuntimeException {
    private String errorCode;

    public InvalidInputException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;

    }

    public InvalidInputException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;

    }
}
