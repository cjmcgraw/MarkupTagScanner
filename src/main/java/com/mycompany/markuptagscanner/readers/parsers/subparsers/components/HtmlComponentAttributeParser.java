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

import java.io.*;

import com.mycompany.markuptagscanner.enums.MarkupTag;
import com.mycompany.markuptagscanner.readers.parsers.*;
import com.mycompany.markuptagscanner.errors.InvalidStateException;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markuptagscanner.errors.EndOfInputAttributeError;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;
import com.mycompany.markuptagscanner.errors.MarkupError;

public abstract class HtmlComponentAttributeParser extends MarkupParser<HtmlAttribute> {
    private static final String CLASS_NAME = "HtmlComponentAttributeParser";
    private static final String FIELD_NAME = "attribute";
    protected HtmlAttribute attribute;
    
    public abstract HtmlAttribute parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.attribute = new HtmlAttribute();
    }
    
    protected void clearState() {
        super.clearState();
        this.attribute = null;
    }
    
    protected char read() throws IOException {
        try {
            return super.read();
        } catch (EOFException e) {
            throw this.generateEndOfInputAttributeException();
        }
    }
    
    protected EndOfInputAttributeError generateEndOfInputAttributeException() {
        this.attribute.setName(this.getAttributeName());
        this.attribute.setValue(this.getAttributeValue());
        return new EndOfInputAttributeError(this.getAttribute());
    }
    
    protected abstract String getAttributeName();
    
    protected abstract String getAttributeValue();
    
    protected boolean isValueSeparator(char c) {
        return MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.equals(c);
    }
    
    protected void addError(MarkupError err) {
        this.validateState();
        this.getAttribute().addError(err);
    }
    
    protected HtmlAttribute getAttribute() {
        this.validateState();
        return this.attribute;
    }
    
    private void validateState() {
        if(this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.attribute == null;
    }
}
