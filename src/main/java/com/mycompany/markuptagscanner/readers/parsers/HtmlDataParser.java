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

import com.mycompany.markuptagscanner.enums.MarkupTag;
import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import com.mycompany.markuptagscanner.readers.parsers.subparsers.*;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;

public class HtmlDataParser extends DataParser{
    private static final String CLASS_NAME = "HtmlDataParser";
    private static final String FIRST_FIELD_NAME = "closingParser";
    private static final String SECOND_FIELD_NAME = "elementParser";
    private static final String THIRD_FIELD_NAME = "attributeParser";
    
    private HtmlSubParser closingParser;
    private HtmlSubParser elementParser;
    private HtmlSubParser attributeParser;
    
    public HtmlDataParser() {
        this(new HtmlClosingSubParser(),
             new HtmlElementSubParser(),
             new HtmlAttributesSubParser());
        }
    
    public HtmlDataParser(HtmlSubParser closingParser,
                          HtmlSubParser elementParser,
                          HtmlSubParser attributeParser) {
        this.closingParser = closingParser;
        this.elementParser = elementParser;
        this.attributeParser = attributeParser;
    }
    
    public HtmlData parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        HtmlData result = this.parseTag();
        this.clearState();
        return result;
    }

    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.getResult().setLocation(getInput().getPosition());
    }
    
    private HtmlData parseTag() throws IOException {
        try {
            this.readOpenTag();
            this.parseTagData();
            this.readCloseTag();
            return this.getResult();
        } catch (NonFatalParsingError e) {
            HtmlData data = e.getHtmlData();
            data.getErrorReporter().addError(e);
            return data;
        }
    }
    
    private void parseTagData() throws IOException {
        try {
            this.parseTagDataInSegments();
        } catch (UnexpectedCloseTagParsingError e) {}
    }
    
    private void parseTagDataInSegments() throws IOException {
        this.parseClosingData();
        this.parseElementName();
        this.parseAttributeData();
    }
    
    private void readOpenTag() throws IOException {
        char c = this.read();
        
        if (!isOpeningTag(c)) {
           throw this.invalidOpeningTagRead();
        }
        this.getResult().confirmOpeningTag();
    }
    
    private ParsingError invalidOpeningTagRead() throws IOException {
        if (!isClosingTag())
            this.unread();
        else
            this.getResult().confirmClosingTag();
        char exp = MarkupTag.OPENING_TAG.toChar();
        return this.getMissingEnclosureParsingException(exp, getCurrChar());
    }
    
    private HtmlData parseClosingData() throws IOException {
        this.validateState();
        return this.closingParser.parse(this.getInput(), this.getResult());
    }
    
    private HtmlData parseElementName() throws IOException {
        this.validateState();
        return this.elementParser.parse(this.getInput(), this.getResult());
    }
    
    private HtmlData parseAttributeData() throws IOException {
        this.validateState();
        return this.attributeParser.parse(this.getInput(), this.getResult());
    }
    
    private void readCloseTag() throws IOException {
        char c = this.read();
        
        if (!isClosingTag(c)) {
            this.unread(c);
            char exp = MarkupTag.CLOSING_TAG.toChar();
            throw this.getMissingEnclosureParsingException(exp, c);
        }
        this.getResult().confirmClosingTag();
    }
    
    private ParsingError getMissingEnclosureParsingException(char missing, char found) {
        return new MissingEnclosureParsingError(this.currentPosition(), missing, found, this.getResult());
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private boolean isMissingState() {
        return this.closingParser == null ||
               this.elementParser == null ||
               this.attributeParser == null;
    }
    
    private String getMissingFieldName() {
        if (this.closingParser == null)
            return FIRST_FIELD_NAME;
        if (this.elementParser == null)
            return SECOND_FIELD_NAME;
        if (this.attributeParser == null)
            return THIRD_FIELD_NAME;
        return "Unknown field is missing!";
    }
}
