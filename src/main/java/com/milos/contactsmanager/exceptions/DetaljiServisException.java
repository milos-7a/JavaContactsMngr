package com.milos.contactsmanager.exceptions;

public class DetaljiServisException extends Exception {
    public DetaljiServisException(String message) {
        super(message);
    }
    public DetaljiServisException(String message, Throwable cause) {
        super(message, cause);
    }
}
