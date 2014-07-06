package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.WhitespaceConsumer;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlAttributeParser extends HtmlUtilityParser {
    private WhitespaceConsumer consumer = new WhitespaceConsumer();
    private HtmlQuoteEnclosureParser parser = new HtmlQuoteEnclosureParser();
    private char currChar;
    
    public void setQuoteEnclosureParser(HtmlQuoteEnclosureParser parser) {
        this.parser = parser;
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseAttributes();
        this.parseCloseTag();
        this.clearState();
        return result;
    }
    
    private void parseAttributes() throws IOException {
        String name = result.getName();
        
        if(MarkupTagNames.SCRIPT_TAG.equals(name))
            this.cycleScriptData();
        
        else if(MarkupTagNames.COMMENT_TAG.equals(name))
            this.parseCommentData();
        
        else
            this.parseAttributesData();
    }
    
    private void cycleScriptData() throws IOException {
        this.readRemainingDataUntilClosingTag();
    }
    
    private void parseCommentData() throws IOException {
        String commentData = this.readRemainingDataUntilClosingTag();
        HtmlAttribute attribute = new HtmlAttribute();
        attribute.setName(commentData);
        
        this.result.updateAttributes(attribute);
    }
    
    private String readRemainingDataUntilClosingTag() throws IOException {
        StringBuilder result = new StringBuilder();
        
        while (this.validateChar()) {
            result.append(this.currChar);
            this.readNext();
        }
        
        return result.toString().trim();
    }
    
    private void parseAttributesData() throws IOException {
        while(this.isSeparator() || this.isClosingAttribute()) {
            HtmlAttribute attr = this.parseAttribute();
            this.result.updateAttributes(attr);
        }
    }
    
    private void parseCloseTag() throws IOException {
        if (!isClosingTag(this.currChar))
            throw new MissingEnclosureParsingException(this.input.getPosition(), this.currChar, this.result);
    }
    
    private HtmlAttribute parseAttribute() throws IOException {
        String name = this.parseAttributeName();
        String value = this.parseAttributeValue();
        return new HtmlAttribute(name, value);
    }
    
    private String parseAttributeName() throws IOException {
        if(this.isClosingAttribute())
            return this.parseAttributeNameClosingTag();
        else
            return this.parseAttributeNameData();
    }
    
    private String parseAttributeNameClosingTag() throws IOException {
        this.readNext();
        this.validateChar();
        return MarkupTag.CLOSING_ATTRIBUTE.toString();
    }
    
    private String parseAttributeNameData() throws IOException {
        StringBuilder result = new StringBuilder();
        this.readNext();
        
        while(this.isValidAttributeChar()) {
            result.append(this.currChar);
            this.readNext();
        }
        
        return result.toString();
    }
    
    private String parseAttributeValue() throws IOException {
        if (!this.isSplitPoint())
            return "";
        this.readNext();
        return this.parseAttributeValueData();
    }
    
    private String parseAttributeValueData() throws IOException {
        if(isQuoteEnclosure())
            return parseAttributeValueInQuoteEnclosure();
        return readAttributeValue();
    }
    
    private String readAttributeValue() throws IOException {
        StringBuilder result = new StringBuilder();
        try {
            this.readAttributeValueData(result);
        } catch (UnexpectedCloseTagParsingException e) {
            this.unread();
        }
        return result.toString();
    }
    
    private void readAttributeValueData(StringBuilder result) throws IOException {
        while(this.isValidAttributeChar()) {
            result.append(this.currChar);
            this.readNext();
        }
    }
    
    private String parseAttributeValueInQuoteEnclosure() throws IOException {
        this.unread();
        this.parser.parse(this.input, this.result);
        this.readNext();
        this.validateChar();
        return this.parser.getQuoteData();
    }
    
    @Override
    public char readNext() throws IOException {
        this.currChar = super.readNext();
        return this.currChar;
    }
    
    private void unread() throws IOException {
        super.unread(this.currChar);
    }
    
    private boolean isValidAttributeChar() throws IOException {
        return (this.validateChar() && 
                !this.isSeparator() &&
                !this.isSplitPoint() &&
                !this.isClosingAttribute());
    }
    
    private boolean isSeparator() {
        return this.currChar == ' ';
    }
    
    private boolean isSplitPoint() {
        return MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.equals(this.currChar);
    }
    
    private boolean isQuoteEnclosure() {
        return isQuoteEnclosure(this.currChar);
    }
    
    private boolean validateChar() throws IOException {
        boolean result = true;
        try {
            result = result && super.validateChar(this.currChar);
        } catch (UnexpectedCloseTagParsingException e) {
            return false;
        }
        return result;
    }
    
    private boolean isClosingAttribute() throws IOException {
        return isClosingAttribute(this.currChar);
    }
    
    @Override
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.currChar = ' ';
    }
}