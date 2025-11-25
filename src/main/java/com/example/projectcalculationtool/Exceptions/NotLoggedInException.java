package com.example.projectcalculationtool.Exceptions;

public class NotLoggedInException extends RuntimeException {
    public NotLoggedInException(String message) {
        super(message);
    }
}
