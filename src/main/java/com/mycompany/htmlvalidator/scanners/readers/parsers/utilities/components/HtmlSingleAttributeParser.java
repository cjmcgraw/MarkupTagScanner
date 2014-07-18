package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.ComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParser extends HtmlComponentAttributeParser {
    private HtmlComponentEnclosureParser quoteEnclosureParser;
    private Consumer whitespaceConsumer;
    private char currChar;
    
    public HtmlSingleAttributeParser(HtmlComponentEnclosureParser quoteEnclosureParser, 
                                     Consumer whitespaceConsumer) {
        this.quoteEnclosureParser = quoteEnclosureParser;
        this.whitespaceConsumer = whitespaceConsumer;
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException{
        this.setState(input);
        HtmlAttribute result = this.attribute;
        
        this.parseAttribute();
        this.clearState();
        return result;
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.currChar = ' ';
    }
    
    @Override
    protected void clearState() {
        super.clearState();
        this.currChar = ' ';
    }
    
    @Override
    protected char read() throws IOException {
        this.currChar = super.read();
        return this.currChar;
    }
    
    protected void unread() throws IOException {
        this.unread(this.currChar);
    }
    
    private void parseAttribute() throws IOException {
        String name = this.parseName();
        this.consumeWhitespace();
        String value = this.parseValue();
        
        this.attribute.setName(name);
        this.attribute.setValue(value);
    }
    
    private String parseName() throws IOException {
        return this.parseGeneralAttributeData();
    }
    
    private String parseValue() throws IOException {
        if(this.attributeIsFlag())
            return "";
        return this.parseValueString();
    }
    
    private boolean attributeIsFlag() throws IOException {
        this.read();
        
        if(this.isValueSeparator())
            return false;
        this.unread();
        return true;
    }
    
    private String parseValueString() throws IOException {
        this.consumeWhitespace();
        if (this.isQuoteEnclosure())
            return this.getQuoteEnclosureData();
        return this.parseGeneralAttributeData();
    }
    
    private String getQuoteEnclosureData() throws IOException {
        String result;
        try {
            result = this.runQuoteEnclosureParser();
        } catch (ComponentException e) {
            result = e.getData();
            this.addError(e);
        }
        return result;
    }
    
    private String runQuoteEnclosureParser() throws IOException {
        return this.quoteEnclosureParser.parse(this.input);
    }
    
    private String parseGeneralAttributeData() throws IOException {
        StringBuilder result = new StringBuilder();
        
        this.read();
         
        while(isValidCharacter()) {
            result.append(this.currChar);
            this.read();
        }
        
        this.unread();
        return result.toString();
    }
    
    private void consumeWhitespace() throws IOException {
        try {
            this.runWhitespaceConsumer();
        } catch (ComponentException e) {
            this.addError(e);
        }
    }
    
    private void runWhitespaceConsumer() throws IOException {
        this.whitespaceConsumer.parse(this.input);
        this.updateCurrentChar();
    }
    
    private void updateCurrentChar() throws IOException {
        this.read();
        this.unread();
    }
    
    private boolean isQuoteEnclosure() {
        return this.isQuoteEnclosure(this.currChar);
    }
    
    private boolean isValueSeparator() {
        return this.isValueSeparator(this.currChar);
    }
    
    private boolean isValidCharacter() {
        return !this.isWhitespace(this.currChar) && 
               !this.isValueSeparator(this.currChar) &&
               !this.isTagEnclosure(this.currChar) && 
               !this.isClosingAttribute(this.currChar);
    }
}