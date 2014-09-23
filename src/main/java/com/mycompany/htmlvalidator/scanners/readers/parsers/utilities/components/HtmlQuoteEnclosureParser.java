package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.enums.EnclosureTags;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

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