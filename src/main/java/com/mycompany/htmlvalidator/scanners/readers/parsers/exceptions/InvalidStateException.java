package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import com.mycompany.htmlvalidator.exceptions.MarkupError;

public class InvalidStateException extends MarkupError {
    private static final long serialVersionUID = 7342202150355963466L;
    private static final String MSG = "Error: State has not been set on <%s>. Cannot perform this operation without <%s> set! Invalid State!!";
    
    public InvalidStateException(String className, String fieldName) {
        super(String.format(MSG, className, fieldName));
    }
}