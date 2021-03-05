package com.epam.jwd.core_final.exception;

public class DublicateException extends RuntimeException {

    public DublicateException() {
        super();
        getMessage();
    }

    @Override
    public String getMessage() {
        return "Dublicate exception";
    }
}
