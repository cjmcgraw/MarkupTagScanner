package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

public abstract class ComponentException extends RuntimeException {
    private static final long serialVersionUID = 8968887910665038407L;
    protected static final String DEFAULT_ERROR_MESSAGE = "ERORR: Component Error in tag -> %s";
    
    public abstract String getErrorMessage();
    
    public abstract String getData();
    
    public String toString() {
        return this.getErrorMessage();
    }
}
