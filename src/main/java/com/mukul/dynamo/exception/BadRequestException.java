package com.mukul.dynamo.exception;

import org.springframework.web.client.HttpStatusCodeException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

public class BadRequestException extends HttpStatusCodeException {

    public BadRequestException(String message) {
        super(BAD_REQUEST, message);
    }
}