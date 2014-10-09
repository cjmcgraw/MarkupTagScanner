package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.errors.MarkupError;

public abstract class AttributeError extends MarkupError{
    private static final long serialVersionUID = 2298941335061905710L;
    protected static final String DEFAULT_ERROR_MSG = "Exception during attribute parsing -> %s";
    
    protected AttributeError(String msg) {
        super(msg);
    }
    
    public abstract HtmlAttribute getAttribute();
    
    public abstract String getErrorMessage();
    
    public String toString() {
        return getErrorMessage();
    }
}