package com.epam.jwd.core_final.exception;

public class InvalidStateException extends Exception {

    public InvalidStateException() {
        super();
        System.out.println(getMessage());
    }

    @Override
    public String getMessage() {
        return "Invalid state exception ";
    }
}

