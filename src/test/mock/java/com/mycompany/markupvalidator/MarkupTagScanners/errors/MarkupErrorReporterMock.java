/*  This file is part of MarkupValidator.
 *
 *  MarkupValidator is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  MarkupValidator is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with MarkupValidator. If not, see <http://www.gnu.org/licenses/>.
 */
package com.mycompany.markupvalidator.MarkupTagScanners.errors;

import java.util.*;

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
    public boolean hasErrors() {
        return !this.data.isEmpty();
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
