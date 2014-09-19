package com.mycompany.htmlvalidator.exceptions;

public abstract class MarkupError extends RuntimeException{
    private static final long serialVersionUID = -5709970001272720858L;
    private static final String MSG = "ERROR: %s";
    
    private String errorMsg; 
    
    public MarkupError(String msg) {
        super(String.format(MSG, msg));
        this.errorMsg = this.getMessage();
    }
    
    protected void logError(ErrorReporter reporter) {
        reporter.addError(this);
    }
    
    public String getErrorMessage() {
        return this.errorMsg;
    }
}
