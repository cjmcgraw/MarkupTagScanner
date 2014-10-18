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

import java.awt.Point;

import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;

public class EndOfInputParsingError extends FatalParsingError {
    private static final long serialVersionUID = 3805654602226525591L;
    private static final String defaultMsg = " END OF INPUT DETECTED. Cannot finish parsing tag";
    private static final char endOfInput = (char) -1;
    
    public EndOfInputParsingError(Point position, HtmlData result) {
        super(position, result, endOfInput, defaultMsg);
    }
}
