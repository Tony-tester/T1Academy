package org.example.exceptions;

public class LimitException extends RuntimeException{
    public LimitException(String message) {
        super(message);
    }
}
