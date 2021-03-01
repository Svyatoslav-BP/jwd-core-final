package com.epam.jwd.core_final.exception;

public class InvalidArgsFactoryException extends RuntimeException {

    private final String entityName;

    public InvalidArgsFactoryException(String entityName) {
        super();
        this.entityName = entityName;
    }

    @Override
    public String getMessage() {
        return "troubles with args of "+entityName;
    }
}
