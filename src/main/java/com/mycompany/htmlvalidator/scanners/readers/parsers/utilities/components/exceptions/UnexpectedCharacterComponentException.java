package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

import java.awt.Point;

public class UnexpectedCharacterComponentException extends ComponentException {
    private static final long serialVersionUID = -5796386130383540454L;
    private static final String UNEXPECTED_CHAR_MSG = "Unexpected Character! At position %s didn't expect ---> %s";
    
    private Point position;
    private char unexpected;
    private Object data;
    
    public UnexpectedCharacterComponentException(char unexpected, Point position, Object data) {
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + ((position == null) ? 0 : position.hashCode());
        result = prime * result + unexpected;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        UnexpectedCharacterComponentException other = (UnexpectedCharacterComponentException) obj;
        if (data == null) {
            if (other.data != null) return false;
        } else if (!data.equals(other.data)) return false;
        if (position == null) {
            if (other.position != null) return false;
        } else if (!position.equals(other.position)) return false;
        if (unexpected != other.unexpected) return false;
        return true;
    }
    
    
}
