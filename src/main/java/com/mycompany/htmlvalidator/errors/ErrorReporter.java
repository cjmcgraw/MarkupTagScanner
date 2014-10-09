package com.mycompany.htmlvalidator.errors;

import java.util.*;

public interface ErrorReporter {
    
    public void addError(MarkupError err);
    
    public void addErrors(Iterable<? extends MarkupError> other);
    
    public Iterator<MarkupError> errorIterator();
    
    public Iterable<MarkupError> getErrors();

    public boolean hasErrors();

    @Override
    public boolean equals(Object other);
    
    @Override
    public int hashCode();
}
