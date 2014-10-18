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
package com.mycompany.markuptagscanner.readers.parsers.subparsers;

import java.io.IOException;

import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import com.mycompany.markuptagscanner.errors.ParsingError;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlTypeSubParserMock extends HtmlSubParser {
    protected ParsingError exception;
    protected HtmlData result;
    
    protected HtmlTypeSubParserMock(ParsingError exception) {
        this.exception = exception;
    }
    
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
        this.setResult(result);
        this.updateResult();
        this.throwApplicableException();
        return this.result;
    }
    
    private void setResult(HtmlData result) {
        this.result = result;
    }
    
    protected abstract void updateResult();
    
    protected void throwApplicableException() {
        beforeException();
        if(this.exception != null) {
            exception.setHtmlData(this.result);
            throw this.exception;
        }
    }
    
    protected void beforeException() {}
}
