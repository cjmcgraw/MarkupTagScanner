package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions;

import com.mycompany.htmlvalidator.exceptions.MarkupError;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;

public abstract class AttributeException extends MarkupError{
    private static final long serialVersionUID = 2298941335061905710L;
    protected static final String DEFAULT_ERROR_MSG = "Exception during attribute parsing -> %s";
    
    protected AttributeException(String msg) {
        super(msg);
    }
    
    public abstract HtmlAttribute getAttribute();
    
    public abstract String getErrorMessage();
    
    public String toString() {
        return getErrorMessage();
    }
}