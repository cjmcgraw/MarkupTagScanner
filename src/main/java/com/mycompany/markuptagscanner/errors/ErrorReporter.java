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
package com.mycompany.markuptagscanner.errors;

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
