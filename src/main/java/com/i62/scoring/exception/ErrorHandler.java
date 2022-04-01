package com.i62.scoring.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleException(Exception ex) {
        log.error("Unexpected exception ", ex);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .code("unexpected_internal_error")
                .message("Unexpected internal error").build();
        return ResponseEntity.status(status).body(responseBody);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequestException(BadRequestException ex) {
        log.error("Bad request exception ", ex);
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ExceptionResponse responseBody = ExceptionResponse.builder()
                .code(ex.getCode())
                .message(ex.getMessage()).build();
        return ResponseEntity.status(status).body(responseBody);
    }
}
