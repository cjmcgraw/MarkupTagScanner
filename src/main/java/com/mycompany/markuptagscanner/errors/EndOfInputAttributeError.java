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

import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;

public class EndOfInputAttributeError extends AttributeError {
    private static final long serialVersionUID = -1561021604836743910L;
    private static final String MSG = "End of input reached on attribute: %s";
    
    private HtmlAttribute attr;
    
    public EndOfInputAttributeError(HtmlAttribute attr) {
        super(String.format(DEFAULT_ERROR_MSG, String.format(MSG, attr)));
        this.attr = attr;
    }

    @Override
    public HtmlAttribute getAttribute() {
        return this.attr;
    }

    @Override
    public String getErrorMessage() {
        return String.format(DEFAULT_ERROR_MSG, String.format(MSG, this.attr));
    }
    
}
