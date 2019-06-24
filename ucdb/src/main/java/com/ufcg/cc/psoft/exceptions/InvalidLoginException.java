package com.ufcg.cc.psoft.exceptions;

public class InvalidLoginException extends RuntimeException {
    public InvalidLoginException(String message) {
        super(message);
    }
}
