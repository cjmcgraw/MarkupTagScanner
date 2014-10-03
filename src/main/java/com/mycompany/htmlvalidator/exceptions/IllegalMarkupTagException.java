package com.mycompany.htmlvalidator.exceptions;

import java.lang.Exception;

public class IllegalMarkupTagException extends Exception {
    private static final long serialVersionUID = 6277791430245190532L;

    public IllegalMarkupTagException() {
        super();
    }
    
    public IllegalMarkupTagException(String message) {
        super(message);
    }
    
    public IllegalMarkupTagException(Throwable cause) {
        super(cause);
    }
    
    public IllegalMarkupTagException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IllegalMarkupTagException(String message,
                                     Throwable cause,
                                     boolean enableSuppression,
                                     boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
