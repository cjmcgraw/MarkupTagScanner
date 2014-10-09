package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;

public class EndOfInputAttributeError extends AttributeError {
    private static final long serialVersionUID = -1561021604836743910L;
    private static final String MSG = "End of input reached on attribute: %s";
    
    private HtmlAttribute attr;
    
    public EndOfInputAttributeError(HtmlAttribute attr) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(MSG, attr)));
        this.attr = attr;
    }

    @Override
    public HtmlAttribute getAttribute() {
        return this.attr;
    }

    @Override
    public String getErrorMessage() {
        return String.format(DEFAULT_ERROR_MSG, String.format(MSG, this.attr));
    }
    
}
