/*  This file is part of MarkupTagScanner.
 *
 *  MarkupTagScanner is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupTagScanner is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupTagScanner. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markuptagscanner.readers.parsers;

import java.awt.Point;
import java.util.*;

import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;

@SuppressWarnings("serial")
public class HtmlAttributeMock extends HtmlAttribute implements ErrorReporter {
    private static final List<MarkupError> DEFAULT_DATA = generateMarkupErrors();
    
    private static List<MarkupError> generateMarkupErrors() {
        List<MarkupError> result = new ArrayList<MarkupError>();
        
        result.add(new MissingCharacterComponentError(' ', new Point(0,0), "some data"));
        result.add(new UnexpectedCharacterComponentError(' ', new Point(0, 0), "Some Data"));
        
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
