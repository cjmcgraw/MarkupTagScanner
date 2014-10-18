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

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markupvalidator.errors.MarkupError;

public abstract class AttributeError extends MarkupError{
    private static final long serialVersionUID = 2298941335061905710L;
    protected static final String DEFAULT_ERROR_MSG = "Exception during attribute parsing -> %s";
    
    protected AttributeError(String msg) {
        super(msg);
    }
    
    public abstract HtmlAttribute getAttribute();
    
    public abstract String getErrorMessage();
    
    public String toString() {
        return getErrorMessage();
    }
}
