package com.example.tradingapp.exceptions;

public class NoOrderFoundException extends Exception {
    public NoOrderFoundException(String errorMessage) {
        super(errorMessage);
    }
}
