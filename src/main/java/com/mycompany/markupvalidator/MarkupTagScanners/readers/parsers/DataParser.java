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
package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers;

import java.io.*;

import com.mycompany.markupvalidator.errors.*;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class DataParser extends MarkupParser<HtmlData> {
    private static final String CLASS_NAME = "DataParser";
    private static final String FIELD_NAME = "result";
    protected HtmlData result;
    
    protected char read() throws IOException {
        try {
            return super.read();
        } catch (EOFException e) {
            throw this.generateEndOfInputParsingException();
        }
    }
    
    protected EndOfInputParsingError generateEndOfInputParsingException() {
        return new EndOfInputParsingError(this.currentPosition(), this.getResult());
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input) throws IOException;
    
    protected boolean validateChar(char c) throws IOException {
        if(isClosingTag(c))
            this.closeTagRead(c);
        else if (isOpeningTag(c))
            this.openTagRead(c);
        
        return true;
    }
    
    private void closeTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnexpectedCloseTagParsingError(this.currentPosition(), this.getResult());
    }
    
    private void openTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnclosedTagParsingError(this.currentPosition(), this.getResult());
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        this.setState(input, new HtmlData());
    }
    
    protected void setState(PushbackAndPositionReader input, HtmlData result) {
        super.setState(input);
        this.result = result;
    }
    
    protected void clearState() {
        super.clearState();
        this.result = null;
    }
    
    protected HtmlData getResult() {
        this.validateState();
        return this.result;
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return result == null;
    }
}
