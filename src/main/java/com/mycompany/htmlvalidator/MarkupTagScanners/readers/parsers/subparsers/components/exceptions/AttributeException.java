package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.exceptions.MarkupError;

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