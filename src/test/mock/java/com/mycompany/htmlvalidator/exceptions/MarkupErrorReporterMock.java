package com.mycompany.htmlvalidator.exceptions;

import java.util.*;

import com.mycompany.htmlvalidator.exceptions.MarkupError;
import com.mycompany.htmlvalidator.exceptions.ErrorReporter;

public class MarkupErrorReporterMock implements ErrorReporter {
    private ArrayList<MarkupError> data;
    private ArrayList<MarkupError> added;
    private boolean iteratorCalled;
    private boolean addCalled;
    
    public MarkupErrorReporterMock() {
        this(new ArrayList<MarkupError>());
    }
    
    public MarkupErrorReporterMock(ArrayList<MarkupError> data) {
        this.data = data;
        this.added = new ArrayList<>();
        this.iteratorCalled = false;
        this.addCalled = false;
    }
    
    public boolean iteratorCalled() {
        return this.iteratorCalled;
    }
    
    public boolean addCalled() {
        return this.addCalled;
    }
    
    public ArrayList<MarkupError> getData() {
        return this.data;
    }
    
    public void setData(Collection<? extends MarkupError> data) {
        this.data = new ArrayList<>();
        
        for(MarkupError err : data) 
            this.data.add(err);
    }
    
    public ArrayList<MarkupError> getAdded() {
        return this.added;
    }
    
    @Override
    public void addError(MarkupError err) {
        this.addCalled = true;
        this.added.add(err);
    }

    @Override
    public void addErrors(Iterable<? extends MarkupError> other) {
        this.addCalled = true;
        for(MarkupError err : other)
            this.added.add(err);
    }

    @Override
    public Iterator<MarkupError> errorIterator() {
        this.iteratorCalled = true;
        return this.data.iterator();
    }

    @Override
    public Iterable<MarkupError> getErrors() {
        this.iteratorCalled = true;
        return this.data;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (addCalled ? 1231 : 1237);
        result = prime * result + ((data == null) ? 0 : data.hashCode());
        result = prime * result + (iteratorCalled ? 1231 : 1237);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        MarkupErrorReporterMock other = (MarkupErrorReporterMock) obj;
        if (addCalled != other.addCalled) return false;
        if (data == null) {
            if (other.data != null) return false;
        } else if (!data.equals(other.data)) return false;
        if (iteratorCalled != other.iteratorCalled) return false;
        return true;
    }
}
