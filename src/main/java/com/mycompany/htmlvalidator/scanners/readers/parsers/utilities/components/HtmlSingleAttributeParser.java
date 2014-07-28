package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components.exceptions.ComponentException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlSingleAttributeParser extends HtmlComponentAttributeParser {
    private static final String CLASS_NAME = "HtmlSingleAttributeParser";
    private static final String FIRST_FIELD_NAME = "quoteEnclosureParser";
    private static final String SECOND_FIELD_NAME = "whitespaceConsumer";
    
    private HtmlComponentEnclosureParser quoteEnclosureParser;
    private Consumer whitespaceConsumer;
    
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
    
    private void parseAttribute() throws IOException {
        String name = this.parseName();
        this.consumeWhitespace();
        String value = this.parseValue();
        
        this.getAttribute().setName(name);
        this.getAttribute().setValue(value);
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
        
        char upcoming = this.peekNextRead();
        
        if (this.isQuoteEnclosure(upcoming))
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
        this.validateState();
        return this.quoteEnclosureParser.parse(this.getInput());
    }
    
    private String parseGeneralAttributeData() throws IOException {
        StringBuilder result = new StringBuilder();
        
        this.read();
         
        while(isValidCharacter()) {
            result.append(this.getCurrChar());
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
        this.validateState();
        this.whitespaceConsumer.parse(this.getInput());
        this.peekNextRead();
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