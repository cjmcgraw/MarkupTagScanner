package com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions;

import com.mycompany.htmlvalidator.exceptions.MarkupError;

public class InvalidStateException extends RuntimeException implements MarkupError {
    private static final long serialVersionUID = 7342202150355963466L;
    private static final String MSG = "Error: State has not been set on <%s>. Cannot perform this operation without <%s> set! Invalid State!!";
    
    private String className;
    private String fieldName;
    
    public InvalidStateException(String className, String fieldName) {
        this.className = className;
        this.fieldName = fieldName;
    }
    
    public String toString() {
        return String.format(MSG, className, fieldName);
    }
}