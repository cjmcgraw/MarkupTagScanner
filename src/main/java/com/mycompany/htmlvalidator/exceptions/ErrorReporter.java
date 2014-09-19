package com.mycompany.htmlvalidator.exceptions;

import java.util.*;

import com.mycompany.htmlvalidator.exceptions.MarkupError;

public interface ErrorReporter {
    
    public void addError(MarkupError err);
    
    public void addErrors(Iterable<? extends MarkupError> other);
    
    public Iterator<MarkupError> errorIterator();
    
    public Iterable<MarkupError> getErrors();
    
    @Override
    public boolean equals(Object other);
    
    @Override
    public int hashCode();
}
