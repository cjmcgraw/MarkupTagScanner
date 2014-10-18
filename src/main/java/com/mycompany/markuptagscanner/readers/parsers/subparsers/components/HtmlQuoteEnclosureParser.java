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
package com.mycompany.markuptagscanner.readers.parsers.subparsers.components;

import java.io.*;
import java.util.*;

import com.mycompany.markuptagscanner.enums.EnclosureTags;
import com.mycompany.markuptagscanner.errors.InvalidStateException;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;

public class HtmlQuoteEnclosureParser extends HtmlComponentEnclosureParser {
    private static final String CLASS_NAME = "HtmlQuoteEnclosureParser";
    private static final String FIELD_NAME = "quoteData";
    private static final Collection<EnclosureTags> VALID_ENCLOSURES= 
            Arrays.asList(EnclosureTags.SINGLE_QUOTE_ENCLOSURE,
                          EnclosureTags.DOUBLE_QUOTE_ENCLOSURE);
    
    private StringBuilder quoteData;
    
    @Override
    public String parse(PushbackAndPositionReader input) throws IOException{
        try {
            this.setState(input);
            return this.parseEnclosure();
        } finally {
            this.clearState();
        }
    }
    
    @Override
    protected String getData() {
        this.validateState();
        return this.getQuoteData().toString();
    }
    
    @Override
    protected Collection<EnclosureTags> getValidEnclosures() {
        return VALID_ENCLOSURES;
    }
    
    private String parseEnclosure() throws IOException {
        this.parseOpenQuote();
        this.parseQuotedData();
        this.parseCloseQuote();
        return this.getData();
    }
    
    private void parseOpenQuote() throws IOException {
        char c = this.read();
        this.setAndValidateOpening(c);
        this.appendData(c);
    }
    
    private void parseCloseQuote() throws IOException {
        char c = this.read();
        this.validateClosing(c);
        this.appendData(c);
    }
    
    private void parseQuotedData() throws IOException {
        char c = this.read();
        while (!this.isClosing(c)) {
            this.appendData(c);
            c = this.read();
        }
        this.unread(c);
    }
    
    private void appendData(char c) {
        this.getQuoteData().append(c);
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.quoteData = new StringBuilder();
    }
    
    @Override
    protected void clearState() {
        super.clearState();
        this.quoteData = null;
    }
    
    private StringBuilder getQuoteData() {
        this.validateState();
        return this.quoteData;
    }
    
    private void validateState() {
        if(this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.quoteData == null;
    }
}
