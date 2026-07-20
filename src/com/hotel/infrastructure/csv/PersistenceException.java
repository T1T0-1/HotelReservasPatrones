package com.hotel.infrastructure.csv;

public final class PersistenceException extends RuntimeException {
    public PersistenceException(String message) { super(message); }
    public PersistenceException(String message, Throwable cause) { super(message, cause); }
}
