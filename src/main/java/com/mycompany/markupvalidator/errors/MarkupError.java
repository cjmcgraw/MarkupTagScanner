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

public abstract class MarkupError extends RuntimeException{
    private static final long serialVersionUID = -5709970001272720858L;
    public static final String MSG = "ERROR: %s";
    
    private String errorMsg; 
    
    public MarkupError(String msg) {
        super(String.format(MSG, msg));
        this.errorMsg = this.getMessage();
    }
    
    protected void logError(ErrorReporter reporter) {
        reporter.addError(this);
    }
    
    public String getErrorMessage() {
        return this.errorMsg;
    }

    public String toString() {
        return errorMsg;
    }
}
