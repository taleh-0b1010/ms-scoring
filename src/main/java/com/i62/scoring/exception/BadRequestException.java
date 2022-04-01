package com.i62.scoring.exception;

public class BadRequestException extends RuntimeException {

    private final String code;
    private final String message;

    public BadRequestException(String message) {
        this.code = "bad_request";
        this.message = message;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
