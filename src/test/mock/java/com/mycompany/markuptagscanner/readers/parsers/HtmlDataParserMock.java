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
package com.mycompany.markuptagscanner.readers.parsers;

import java.io.IOException;
import java.util.List;

import com.mycompany.markuptagscanner.errors.ParsingError;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParserMock extends DataParser {
    private List<HtmlData> data;
    private int currIndex;
    private char openChar;
    private char closeChar;
    
    private ParsingError err;
    
    public HtmlDataParserMock(List<HtmlData> data) {
        this.data = data;
        this.currIndex = 0;
        this.err = null;
    }
    
    public void setException(ParsingError err) {
        this.err = err;
    }
    
    public ParsingError getException() {
        return err;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        if (err != null)
            throw err;
        
        this.openChar = (char) input.read();
        this.closeChar = (char) input.read();
        HtmlData result = data.get(this.currIndex);
        this.currIndex++;
        return result;
        
    }
    
    public char getOpenChar() {
        return this.openChar;
    }
    
    public char getCloseChar() {
        return this.closeChar;
    }
}
