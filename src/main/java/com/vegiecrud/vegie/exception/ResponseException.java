package com.vegiecrud.vegie.exception;

import org.springframework.http.HttpStatus;

public class ResponseException extends RuntimeException{
    private final String errorCode;
    private final String message;
    private final HttpStatus httpStatus;

    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public ResponseException(String errorCode, String message, HttpStatus httpStatus) {
        this.errorCode = errorCode;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}
