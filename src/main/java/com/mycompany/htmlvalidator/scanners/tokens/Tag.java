package com.mycompany.htmlvalidator.scanners.tokens;

import java.io.Serializable;

import com.mycompany.htmlvalidator.exceptions.ErrorReporter;

public interface Tag extends Serializable{
    public abstract boolean hasOpeningBracket();
    
    public abstract boolean hasClosingBracket();
    
    public abstract String getName();
    
    public abstract Iterable<Attribute> getAttributes();
    
    public abstract boolean isClosing();
    
    public abstract boolean isSelfClosing();
    
    public abstract ErrorReporter getErrorReporter();
}
