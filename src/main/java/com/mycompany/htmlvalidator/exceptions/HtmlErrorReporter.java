package com.mycompany.htmlvalidator.exceptions;

import java.util.*;

public class HtmlErrorReporter implements MarkupErrorReporter{
    private Collection<MarkupError> markupErrors;
    
    public HtmlErrorReporter() {
        this(new LinkedList<MarkupError>());
    }
    
    public HtmlErrorReporter(Collection<MarkupError> errs) {
        this.markupErrors = errs;
    }

    @Override
    public void addError(MarkupError err) {
        this.markupErrors.add(err);
        
    }

    @Override
    public void addErrors(Iterable<? extends MarkupError> other) {
        for(MarkupError err : other)
            this.addError(err);
    }

    @Override
    public Iterator<MarkupError> errorIterator() {
        return this.markupErrors.iterator();
    }

    @Override
    public Iterable<MarkupError> getErrors() {
        return this.markupErrors;
    }
    
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((markupErrors == null) ? 0 : markupErrors.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        HtmlErrorReporter other = (HtmlErrorReporter) obj;
        if (markupErrors == null) {
            if (other.markupErrors != null) return false;
        } else if (!markupErrors.equals(other.markupErrors)) return false;
        return true;
    }
}
