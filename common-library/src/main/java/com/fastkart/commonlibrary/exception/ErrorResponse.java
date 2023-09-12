package com.fastkart.commonlibrary.exception;

import lombok.Data;

@Data
public class ErrorResponse {
    private final String message;
    private final Integer errorCode;
    private final String errorReason;
}
