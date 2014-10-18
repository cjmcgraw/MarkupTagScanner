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
package com.mycompany.markuptagscanner.readers.parsers.subparsers.components;

import java.io.IOException;
import java.util.*;

import com.mycompany.markuptagscanner.enums.EnclosureTags;
import com.mycompany.markuptagscanner.errors.ComponentError;
import com.mycompany.markuptagscanner.readers.utilities.*;

public class HtmlQuoteEnclosureParserMock extends HtmlComponentEnclosureParser {
    private List<String> receivedData;
    private ComponentError error;
    private String result;
    
    public HtmlQuoteEnclosureParserMock(String result) {
        this(result, null);
    }
    
    public HtmlQuoteEnclosureParserMock(String result, ComponentError error) {
        this.receivedData = new ArrayList<>();
        this.result = result;
        this.error = error;
    }
    
    @Override
    protected String getData() {
        return result;
    }
    
    @Override
    protected Collection<EnclosureTags> getValidEnclosures() {
        return null;
    }
    
    public List<String> getReceivedData() {
        return this.receivedData;
    }
    
    public ComponentError getError() {
        return this.error;
    }
    
    public void setError(ComponentError err) {
        this.error = err;
    }
    
    @Override
    public String parse(PushbackAndPositionReader input) throws IOException {
        if (this.error != null) {
            input.read();
            throw this.error;
        }
        return this.parseHelper(input);
    }
   
   private String parseHelper(PushbackAndPositionReader input) throws IOException {
        PushbackAndPositionReaderMock mockInput = (PushbackAndPositionReaderMock) input;
        this.receivedData.add(mockInput.getRemainingData());
        mockInput.read();
        return this.result;
    }
    
}
