package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlAttributeParser extends HtmlUtilityParser {
    public static final String ignoreAttributesName = HtmlAttribute.ignoreAttributesName;
    public static final char attributeSeparator = HtmlAttribute.attributeSeparator;
    public static final char attributeSplitter = HtmlAttribute.attributeSplitter;
    
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
        if(result.getName().equals(ignoreAttributesName))
            this.cycleScriptData();
        else
            this.parseAttributesData();
    }
    
    private void parseAttributesData() throws IOException {
        while(this.isSeparator() || this.isClosingTag()) {
            HtmlAttribute attr = this.parseAttribute();
            this.result.updateAttributes(attr);
        }
    }
    
    private void parseCloseTag() throws IOException {
        if (!isCloseTagEnclosure(this.currChar))
            throw new MissingEnclosureParsingException(this.input.getPosition(), this.currChar, this.result);
    }
    
    private HtmlAttribute parseAttribute() throws IOException {
        String name = this.parseAttributeName();
        String value = this.parseAttributeValue();
        return new HtmlAttribute(name, value);
    }
    
    private String parseAttributeName() throws IOException {
        if(this.isClosingTag())
            return this.parseAttributeNameClosingTag();
        else
            return this.parseAttributeNameData();
    }
    
    private String parseAttributeNameClosingTag() throws IOException {
        this.readNext();
        this.validateChar();
        return "" + CLOSING_TAG;
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
    
    private void cycleScriptData() throws IOException {
        while(this.validateChar()) {
            this.readNext();
        }
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
                !this.isClosingTag());
    }
    
    private boolean isSeparator() {
        return this.currChar == attributeSeparator;
    }
    
    private boolean isSplitPoint() {
        return this.currChar == attributeSplitter;
    }
    
    private boolean isQuoteEnclosure() {
        return isQuoteTagEnclosure(this.currChar);
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
    
    private boolean isClosingTag() throws IOException {
        return isClosingTag(this.currChar);
    }
    
    @Override
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.currChar = attributeSeparator;
    }
}