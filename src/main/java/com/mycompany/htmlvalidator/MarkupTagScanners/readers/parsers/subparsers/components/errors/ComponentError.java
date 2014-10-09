package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors;

import com.mycompany.htmlvalidator.errors.MarkupError;

public abstract class ComponentError extends MarkupError{
    private static final long serialVersionUID = 8968887910665038407L;
    protected static final String DEFAULT_ERROR_MSG = "Component Error in tag -> %s";
    
    protected ComponentError(String msg) {
        super(msg);
    }
    
    public abstract String getErrorMessage();
    
    public abstract String getData();
    
    public String toString() {
        return this.getErrorMessage();
    }
    
}
