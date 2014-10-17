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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors.AttributeError;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParserMock extends HtmlSingleAttributeParser {
    private HtmlAttribute result;
    private AttributeError err;
    
    public HtmlSingleAttributeParserMock(String result) {
        this.result = new HtmlAttribute(result);
    }
    
    public HtmlSingleAttributeParserMock(String result, AttributeError err) {
        this(result);
        this.err = err;
    }
    
    public void setError(AttributeError err) {
        this.err = err;
    }
    
    public AttributeError getError() {
        return this.err;
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        input.read();
        if (err != null)
            throw this.err;
        return this.result;
    }
}
