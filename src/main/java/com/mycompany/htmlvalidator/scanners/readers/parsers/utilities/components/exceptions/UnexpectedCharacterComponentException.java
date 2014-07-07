package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

import java.awt.Point;

public class UnexpectedCharacterComponentException extends ComponentException {
    private static final long serialVersionUID = -5796386130383540454L;
    private static final String UNEXPECTED_CHAR_MSG = "Unexpected Character! At position %s didn't expect ---> %s";
    
    private Point position;
    private char unexpected;
    private Object data;
    
    public UnexpectedCharacterComponentException(Point position, char unexpected, Object data) {
        this.position = position;
        this.unexpected = unexpected;
        this.data = data;
    }
    
    public String getData() {
        return this.data.toString();
    }

    @Override
    public String getErrorMessage() {
        String msg = String.format(UNEXPECTED_CHAR_MSG, this.position, this.unexpected);
        return String.format(DEFAULT_ERROR_MESSAGE, msg);
    }
}
