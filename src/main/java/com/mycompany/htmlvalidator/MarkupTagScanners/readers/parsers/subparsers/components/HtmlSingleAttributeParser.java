package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlAttribute;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers.components.exceptions.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParser extends HtmlComponentAttributeParser {
    private static final String CLASS_NAME = "HtmlSingleAttributeParser";
    private static final String FIRST_FIELD_NAME = "quoteEnclosureParser";
    private static final String SECOND_FIELD_NAME = "whitespaceConsumer";
    
    private HtmlComponentEnclosureParser quoteEnclosureParser;
    private Consumer whitespaceConsumer;
    
    private StringBuilder attributeName;
    private StringBuilder attributeValue;
    
    public HtmlSingleAttributeParser() {
        this(new HtmlQuoteEnclosureParser(),
             new WhitespaceConsumer());
    }
    
    public HtmlSingleAttributeParser(HtmlComponentEnclosureParser quoteEnclosureParser, 
                                     Consumer whitespaceConsumer) {
        super();
        this.quoteEnclosureParser = quoteEnclosureParser;
        this.whitespaceConsumer = whitespaceConsumer;
    }
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException{
        this.setState(input);
        HtmlAttribute result = this.getAttribute();
        
        this.parseAttribute();
        this.clearState();
        return result;
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.attributeName = new StringBuilder();
        this.attributeValue = new StringBuilder();
    }
    
    @Override
    protected void clearState() {
        super.clearState();
        this.attributeName = null;
        this.attributeValue = null;
    }
    
    @Override
    protected String getAttributeName() {
        return this.attributeName.toString();
    }
    
    @Override
    protected String getAttributeValue() {
        return this.attributeValue.toString();
    }
    
    private void parseAttribute() throws IOException {
        this.parseName();
        this.consumeWhitespace();
        this.parseValue();
        
        this.getAttribute().setName(this.getAttributeName());
        this.getAttribute().setValue(this.getAttributeValue());
    }
    
    private void parseName() throws IOException {
        char upcoming = this.peekNextRead();
        
        if(this.isClosingAttribute(upcoming))
            this.parseClosingAttributeData();
        else
            this.parseGeneralAttributeData(this.attributeName);
    }
    
    private void parseValue() throws IOException {
        if(!this.attributeIsFlag())
            this.parseValueString();
    }
    
    private boolean attributeIsFlag() throws IOException {
        this.read();
        
        if(this.isValueSeparator())
            return false;
        this.unread();
        return true;
    }
    
    private void parseValueString() throws IOException {
        this.consumeWhitespace();
        
        char upcoming = this.peekNextRead();
        
        if (this.isQuoteEnclosure(upcoming))
            this.getQuoteEnclosureData(this.attributeValue);
        else
            this.parseGeneralAttributeData(this.attributeValue);
    }
    
    private void getQuoteEnclosureData(StringBuilder s) throws IOException {
        
        try {
            s.append(runQuoteEnclosureParser());
        } catch (EndOfInputComponentException e) {
            this.attributeValue = new StringBuilder(e.getData());
            throw this.generateEndOfInputAttributeException();
        } catch (ComponentException e) {
            s.append(e.getData());
            this.addError(e);
        }
    }
    
    private String runQuoteEnclosureParser() throws IOException {
        this.validateState();
        return this.quoteEnclosureParser.parse(this.getInput());
    }
    
    private void parseClosingAttributeData() throws IOException {
        this.read();
        this.attributeName.append("" + this.currChar);
        this.attributeValue = new StringBuilder();
    }
    
    private String parseGeneralAttributeData(StringBuilder s) throws IOException {
        this.read();
         
        while(isValidCharacter()) {
            s.append(this.getCurrChar());
            this.read();
        }
        
        this.unread();
        return s.toString();
    }
    
    private void consumeWhitespace() throws IOException {
        try {
            this.runWhitespaceConsumer();
        } catch (EndOfInputComponentException e) {
            throw this.generateEndOfInputAttributeException();
        } catch (ComponentException e) {
            this.addError(e);
        }
    }
    
    private void runWhitespaceConsumer() throws IOException {
        this.validateState();
        this.whitespaceConsumer.parse(this.getInput());
    }
    
    private boolean isValidCharacter() {
        return !this.isWhitespace(this.getCurrChar()) && 
               !this.isValueSeparator() &&
               !this.isTagEnclosure() && 
               !this.isClosingAttribute();
    }
    
    private boolean isValueSeparator() {
        return this.isValueSeparator(this.getCurrChar());
    }
    
    private void validateState() {
        if(this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private String getMissingFieldName() {
        if (this.quoteEnclosureParser == null)
            return FIRST_FIELD_NAME;
        if (this.whitespaceConsumer == null)
            return SECOND_FIELD_NAME;
        return "Unknown field is missing!";
    }
    
    private boolean isMissingState() {
        return this.quoteEnclosureParser == null ||
               this.whitespaceConsumer == null;
    }
}