package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

import com.mycompany.htmlvalidator.exceptions.MarkupError;

public abstract class ComponentException extends RuntimeException implements MarkupError{
    private static final long serialVersionUID = 8968887910665038407L;
    protected static final String DEFAULT_ERROR_MSG = "ERROR: Component Error in tag -> %s";
    
    protected ComponentException() {super();}
    
    protected ComponentException(String msg) {
        super(msg);
    }
    
    public abstract String getErrorMessage();
    
    public abstract String getData();
    
    public String toString() {
        return this.getErrorMessage();
    }
    
}
