package com.ml.mutant.exception;

public class ConverterException extends RuntimeException {

    public ConverterException(String message) {
        super(message);
    }

    public ConverterException(Exception exception) {
        super(exception);
    }

}
