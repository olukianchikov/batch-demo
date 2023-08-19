package com.lukianchikov.lego.processor.batch.exception;

public class ItemInvalidException extends RuntimeException {
    public ItemInvalidException(String message, Throwable cause) {
        super(message, cause);
    }
}
