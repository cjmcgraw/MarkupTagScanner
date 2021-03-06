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

import java.io.*;

import com.mycompany.markuptagscanner.enums.MarkupTagNames;
import com.mycompany.markuptagscanner.errors.*;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.markuptagscanner.readers.parsers.tokens.HtmlData;
import com.mycompany.markuptagscanner.readers.parsers.subparsers.components.*;
import com.mycompany.markuptagscanner.readers.utilities.PushbackAndPositionReader;

public class HtmlAttributesSubParser extends HtmlSubParser {
    private static final String CLASS_NAME = "HtmlAttributeParser";
    private static final String FIRST_FIELD_NAME = "enclosureParser";
    private static final String SECOND_FIELD_NAME = "parser";
    private static final String THIRD_FIELD_NAME = "commentParser";
    private static final String FOURTH_FIELD_NAME = "consumer";
    
    private static final String COMMENT_NAME = MarkupTagNames.COMMENT_TAG.getBeginName();
    
    private HtmlComponentEnclosureParser enclosureParser;
    private HtmlComponentAttributeParser attributeParser;
    private HtmlComponentAttributeParser commentParser;
    private Consumer whitespaceConsumer;
    
    public HtmlAttributesSubParser() {
        this(new HtmlQuoteEnclosureParser(),
             new HtmlSingleAttributeParser(),
             new HtmlCommentAttributeParser(),
             new WhitespaceConsumer());
    }
    
    public HtmlAttributesSubParser(HtmlComponentEnclosureParser enclosureParser,
                                   HtmlComponentAttributeParser attributeParser,
                                   HtmlComponentAttributeParser commentParser,
                                   Consumer whitespaceConsumer) {
        super();
        this.enclosureParser = enclosureParser;
        this.attributeParser = attributeParser;
        this.commentParser = commentParser;
        this.whitespaceConsumer = whitespaceConsumer;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
        this.setState(input, result);
        this.parseAttributes();
        return result;
    }
    
    private void parseAttributes() throws IOException {
        if (this.resultIsComment())
            this.parseCommentData();
        else 
            this.parseStandardAttributes();
    }
    
    private boolean resultIsComment() {
        return this.getResult().getName().startsWith(COMMENT_NAME);
    }
    
    private void parseCommentData() throws IOException{
        HtmlAttribute attr;
        
        try {
            attr = this.parseCommentAttribute();
            this.getResult().updateAttributes(attr);
        } catch (EndOfInputAttributeError e) {
            attr = e.getAttribute();
            this.getResult().updateAttributes(attr);
            throw this.generateEndOfInputParsingException();
        } 
    }
    
    private void parseStandardAttributes() throws IOException {
        this.updateCurrentCharacter();
        
        while(this.isValidCharacter()) {
            this.updateResultAttributes();
            this.updateCurrentCharacter();
        }
    }
    
    private void updateCurrentCharacter() throws IOException {
        this.consumeWhitespace();
        this.read();
    }
    
    private boolean isValidCharacter() throws IOException {
        try {
            return this.validateChar(this.getCurrChar());
        } catch (UnexpectedCloseTagParsingError e) {
            return false;
        }
    }
    
    private void consumeWhitespace() throws IOException {
        try {
            this.validateState();
            this.whitespaceConsumer.parse(this.getInput());
        } catch (EndOfInputComponentError e) {
            throw this.generateEndOfInputParsingException();
        }
    }
    
    private void updateResultAttributes() throws IOException {
        HtmlAttribute attribute = this.getAttribute();
        this.getResult().updateAttributes(attribute);
    }
    
    private HtmlAttribute getAttribute() throws IOException {
        if (this.isQuoteEnclosure()) 
            return this.parseQuoteEnclosure();
        return this.parseGeneralAttribute();
    }
    
    private HtmlAttribute parseCommentAttribute() throws IOException {
        this.validateState();
        return this.commentParser.parse(this.getInput());
    }
    
    private HtmlAttribute parseQuoteEnclosure() throws IOException {
        this.validateState();
        this.unread();
        return this.getEnclosureAttributeData();
    }
    
    private HtmlAttribute getEnclosureAttributeData() throws IOException {
        String name;
        HtmlAttribute result = new HtmlAttribute();
        
        try {
            name = this.getEnclosureDataName();
        } catch (ComponentError err) {
            name = err.getData();
            result.addError(err);
        }
        result.setName(name);
        return result;
    }
    
    private String getEnclosureDataName() throws IOException {
        try {
            return this.enclosureParser.parse(this.getInput());
        } catch (EndOfInputComponentError e) {
            HtmlAttribute attr = new HtmlAttribute(e.getData());
            this.getResult().updateAttributes(attr);
            throw this.generateEndOfInputParsingException();
        }
    }
    
    private HtmlAttribute parseGeneralAttribute() throws IOException {
        try {
            this.validateState();
            this.unread();
            return this.attributeParser.parse(this.getInput());
        } catch (EndOfInputAttributeError e) {
            this.getResult().updateAttributes(e.getAttribute());
            throw this.generateEndOfInputParsingException();
        }
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private boolean isMissingState() {
        return this.enclosureParser == null ||
               this.attributeParser == null ||
               this.commentParser == null ||
               this.whitespaceConsumer == null;
    }
    
    private String getMissingFieldName() {
        if (this.enclosureParser == null)
            return FIRST_FIELD_NAME;
        if (this.attributeParser == null)
            return SECOND_FIELD_NAME;
        if (this.commentParser == null)
            return THIRD_FIELD_NAME;
        if (this.whitespaceConsumer == null)
            return FOURTH_FIELD_NAME;
        return "Unknown Field Caused Error";
    }
}
