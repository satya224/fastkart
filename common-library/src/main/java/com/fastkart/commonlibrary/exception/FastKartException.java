package com.fastkart.commonlibrary.exception;

import lombok.Getter;

@Getter
public class FastKartException extends RuntimeException {
    private final String message;
    private final Integer errorCode;
    private final String errorReason;

    public FastKartException(String message, Integer errorCode, String errorReason) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
        this.errorReason = errorReason;
    }


}
