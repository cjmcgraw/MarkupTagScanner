package com.mycompany.htmlvalidator.exceptions;

import java.lang.Exception;

public class IllegalHtmlTagException extends Exception {
    private static final long serialVersionUID = 6277791430245190532L;

    public IllegalHtmlTagException() {
        super();
    }
    
    public IllegalHtmlTagException(String message) {
        super(message);
    }
    
    public IllegalHtmlTagException(Throwable cause) {
        super(cause);
    }
    
    public IllegalHtmlTagException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public IllegalHtmlTagException(String message,
                          Throwable cause,
                          boolean enableSuppression,
                          boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
