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

public abstract class ComponentError extends MarkupError{
    private static final long serialVersionUID = 8968887910665038407L;
    protected static final String DEFAULT_ERROR_MSG = "Component Error in tag -> %s";
    
    protected ComponentError(String msg) {
        super(msg);
    }
    
    public abstract String getErrorMessage();
    
    public abstract String getData();
    
    public String toString() {
        return this.getErrorMessage();
    }
    
}
