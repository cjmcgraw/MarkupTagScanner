package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

import java.awt.Point;

public class MissingCharacterComponentException extends ComponentException {
    private static final long serialVersionUID = -4066078864219639731L;
    private static final String MISSING_CHAR_MSG = "Missing Character! At position %s, expected ---> %s";
    
    private Point position;
    private char missing;
    private Object data;
    
    public MissingCharacterComponentException(char missing, Point position, Object data) {
        this.missing = missing;
        this.position = position;
        this.data = data;
    }
    
    public String getData() {
        return this.data.toString();
    }
    
    @Override
    public String getErrorMessage() {
        String msg = String.format(MISSING_CHAR_MSG, this.position, this.missing);
        return String.format(DEFAULT_ERROR_MESSAGE, msg);
    }
    
}
