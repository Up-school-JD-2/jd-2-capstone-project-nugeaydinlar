package io.upschool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestExcepiton extends RuntimeException {

    public BadRequestExcepiton(String message) {
        super(message);
    }

    public BadRequestExcepiton(String message, Throwable cause) {
        super(message, cause);
    }
}