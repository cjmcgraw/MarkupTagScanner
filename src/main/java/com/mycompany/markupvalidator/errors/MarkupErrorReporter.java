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
package com.mycompany.markupvalidator.errors;

import java.io.Serializable;
import java.util.*;

public class MarkupErrorReporter implements ErrorReporter, Serializable{
    private static final long serialVersionUID = 8255260749083572783L;
    private Collection<MarkupError> markupErrors;
    
    public MarkupErrorReporter() {
        this(new LinkedList<MarkupError>());
    }
    
    public MarkupErrorReporter(Collection<MarkupError> errs) {
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
    public boolean hasErrors() {
        return !this.markupErrors.isEmpty();
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
        MarkupErrorReporter other = (MarkupErrorReporter) obj;
        if (markupErrors == null) {
            if (other.markupErrors != null) return false;
        } else if (!markupErrors.equals(other.markupErrors)) return false;
        return true;
    }
}
