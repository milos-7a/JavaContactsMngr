package com.milos.contactsmanager.exceptions;

public class KontaktServisException extends Exception {
    public KontaktServisException(String message) {
        super(message);
    }
    public KontaktServisException(String message, Throwable cause) {
        super(message, cause);
    }
}