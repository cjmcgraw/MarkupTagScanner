package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.awt.Point;
import java.util.*;

import com.mycompany.htmlvalidator.exceptions.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.*;

@SuppressWarnings("serial")
public class HtmlAttributeMock extends HtmlAttribute implements ErrorReporter {
    private static final List<MarkupError> DEFAULT_DATA = generateMarkupErrors();
    
    private static List<MarkupError> generateMarkupErrors() {
        List<MarkupError> result = new ArrayList<MarkupError>();
        
        result.add(new MissingCharacterComponentException(' ', new Point(0,0), "some data"));
        result.add(new UnexpectedCharacterComponentException(' ', new Point(0, 0), "Some Data"));
        
        return result;
    }
    
    private boolean addErrorCalled;
    private MarkupError addedError;
    
    private boolean addErrorsCalled;
    private Iterable<? extends MarkupError> addedErrors;
    
    private boolean errorIteratorCalled;
    private boolean getErrorsCalled;
    
    @Override
    public void addError(MarkupError err) {
        this.setAddedError(err);
        this.setAddErrorCalled(true);
        
    }
    
    @Override
    public void addErrors(Iterable<? extends MarkupError> other) {
        this.setAddedErrors(other);
        this.setAddErrorsCalled(true);
    }
    
    @Override
    public Iterator<MarkupError> errorIterator() {
        return DEFAULT_DATA.iterator();
    }
    
    @Override
    public Iterable<MarkupError> getErrors() {
        return DEFAULT_DATA;
    }

    public MarkupError getAddedError() {
        return addedError;
    }

    public void setAddedError(MarkupError addedError) {
        this.addedError = addedError;
    }

    public boolean AddErrorCalled() {
        return addErrorCalled;
    }

    public void setAddErrorCalled(boolean addErrorCalled) {
        this.addErrorCalled = addErrorCalled;
    }

    public boolean AddErrorsCalled() {
        return addErrorsCalled;
    }

    public void setAddErrorsCalled(boolean addErrorsCalled) {
        this.addErrorsCalled = addErrorsCalled;
    }

    public Iterable<? extends MarkupError> getAddedErrors() {
        return addedErrors;
    }

    public void setAddedErrors(Iterable<? extends MarkupError> addedErrors) {
        this.addedErrors = addedErrors;
    }

    public boolean isErrorIteratorCalled() {
        return errorIteratorCalled;
    }

    public void setErrorIteratorCalled(boolean errorIteratorCalled) {
        this.errorIteratorCalled = errorIteratorCalled;
    }

    public boolean isGetErrorsCalled() {
        return getErrorsCalled;
    }

    public void setGetErrorsCalled(boolean getErrorsCalled) {
        this.getErrorsCalled = getErrorsCalled;
    }
    
}
