package com.example.tradingapp;

public class NoOrderFoundException extends Exception {
    public NoOrderFoundException(String errorMessage) {
        super(errorMessage);
    }
}
