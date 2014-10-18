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
import java.util.*;

import com.mycompany.markupvalidator.MarkupTagScanners.errors.ComponentError;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.*;

public class WhitespaceConsumerMock extends Consumer {
    private List<String> receivedData;
    private ComponentError error;
    
    public WhitespaceConsumerMock() {
        this(null);
    }
    
    public WhitespaceConsumerMock(ComponentError error) {
        this.receivedData = new ArrayList<String>();
        this.error = error;
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
    public Integer parse(PushbackAndPositionReader input) throws IOException {
        if(this.error != null)
            throw this.error;
        return this.parseHelper(input);
    }
    
    private Integer parseHelper(PushbackAndPositionReader input) throws IOException {
        PushbackAndPositionReaderMock mockInput = (PushbackAndPositionReaderMock) input;
        String data = mockInput.getRemainingData();
        this.receivedData.add(data);
        
        if(data.length() > 0) {
            this.removeWhitespace(input);
        }
        
        return 0;
    }
    
    private void removeWhitespace(PushbackAndPositionReader input) throws IOException {
        String data = this.receivedData.get(this.receivedData.size() - 1);
        
        int i = 0;
        char ch = data.charAt(i);
        
        while(this.isWhitespace(ch)) {
            input.read();
            i++;
            ch = data.charAt(i);
        }
    }
    
}
