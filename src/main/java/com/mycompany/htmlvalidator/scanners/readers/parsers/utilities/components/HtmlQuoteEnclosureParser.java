package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlQuoteEnclosureParser extends HtmlComponentEnclosureParser {
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
        return this.quoteData.toString();
    }
    
    @Override
    protected Collection<EnclosureTags> getValidEnclosures() {
        return VALID_ENCLOSURES;
    }
    
    private String parseEnclosure() throws IOException {
        this.parseOpenQuote();
        this.parseQuotedData();
        this.parseCloseQuote();
        return this.quoteData.toString();
    }
    
    private void parseOpenQuote() throws IOException {
        char c = this.read();
        this.setAndValidateOpening(c);
        this.quoteData.append(c);
    }
    
    private void parseCloseQuote() throws IOException {
        char c = this.read();
        this.validateClosing(c);
        this.quoteData.append(c);
    }
    
    private void parseQuotedData() throws IOException {
        char c = this.read();
        while (!this.isClosing(c)) {
            this.quoteData.append(c);
            c = this.read();
        }
        this.unread(c);
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
}