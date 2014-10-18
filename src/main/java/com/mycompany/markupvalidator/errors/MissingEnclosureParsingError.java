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

import java.awt.Point;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;

public class MissingEnclosureParsingError extends NonFatalParsingError {
    private static final long serialVersionUID = -8566122934275099978L;
    private static final String defaultMsg = "MISSING EXPECTED ENCLOSURE! Enclosure improperly formed, " +
                                             "expected [ %s ] but that value was missing!";
    
    public MissingEnclosureParsingError(Point position, char expChar, char errorChar, HtmlData result) {
        super(position, result, errorChar, String.format(defaultMsg, expChar));
    }
    
    public boolean equals(Object other) {
        return getClass() == other.getClass();
    }
}
