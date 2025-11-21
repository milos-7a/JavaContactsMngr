package com.milos.contactsmanager.exceptions;


public class KontaktDAOException extends Exception {
    public KontaktDAOException(String message) {
        super(message);
    }
    public KontaktDAOException(String message, Throwable cause) {
        super(message, cause);
    }
}
