package com.formation.apirestdemo.exception;
/**
 * Exception levée lors d'un conflit (doublon, contrainte violée).
 */
public class ConflitException extends RuntimeException {
    public ConflitException(String message) {
        super(message);
    }
}
