package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlQuoteEnclosureParser extends HtmlParser {
    public static final char quoteEnclosure = '"';
    
    private PushbackAndPositionReader input;
    private MutableHtmlData result;
    private StringBuilder quoteData;
    
    public String getQuoteData () {
        return this.quoteData.toString();
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseQuote();
        this.parseQuotedData();
        this.parseQuote();
        
        result = this.result;
        this.clearState();
        return result;
    }
    
    private void parseQuote() throws IOException {
        char c = this.readNext();
        this.validateEnclosure(c);
        this.quoteData.append(c);
    }
    
    private void parseQuotedData() throws IOException {
        char c;
        while ((c = this.readNext()) != quoteEnclosure) {
            this.quoteData.append(c);
        }
        
        this.input.unread(c);
    }
    
    private void validateEnclosure(char c) throws IOException {
        if(c != quoteEnclosure) {
            this.input.unread(c);
            throw new MissingEnclosureParsingException(this.input.getPosition(),
                                                       quoteEnclosure, this.result);
        }
    }
    
    private char readNext() throws IOException {
        int value = this.input.read();
        this.validateValue(value);
        
        return (char) value;
    }
    
    private void validateValue(int value) {
        if(value == -1)
            throw new EndOfInputParsingException(this.input.getPosition(), this.result);
    }
    
    private void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        this.input = input;
        this.result = result;
        this.quoteData = new StringBuilder();
    }
    
    private void clearState() {
        this.input = null;
        this.result = null;
    }
    
}