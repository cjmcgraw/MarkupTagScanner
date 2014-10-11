package com.mycompany.markupvalidator.MarkupTagScanners;

import com.mycompany.markupvalidator.errors.ErrorReporter;

import java.awt.*;
import java.io.Serializable;

public interface Tag extends Serializable{
    public abstract boolean hasOpeningBracket();
    
    public abstract boolean hasClosingBracket();
    
    public abstract String getName();
    
    public abstract Iterable<Attribute> getAttributes();
    
    public abstract boolean isClosing();
    
    public abstract boolean isSelfClosing();

    public Point location();
    
    public abstract ErrorReporter getErrorReporter();
}
