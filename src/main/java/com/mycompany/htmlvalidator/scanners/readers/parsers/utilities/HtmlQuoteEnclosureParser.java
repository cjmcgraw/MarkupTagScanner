package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlQuoteEnclosureParser extends HtmlUtilityParser {
    private StringBuilder quoteData;
    
    public String getQuoteData () {
        return this.quoteData.toString();
    }
    
    private void setQuoteData(StringBuilder data) {
        this.quoteData = data;
    }
    
    private void updateQuoteData(char c) {
        this.quoteData.append(c);
    }
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.parseQuote();
        this.parseQuotedData();
        this.parseQuote();
        StringBuilder quoteResult = this.quoteData;
        
        this.clearState();
        this.setQuoteData(quoteResult);
        
        return result;
    }
    
    private void parseQuote() throws IOException {
        char c = this.readNext();
        this.validateEnclosure(c);
        this.updateQuoteData(c);
    }
    
    private void parseQuotedData() throws IOException {
        char c;
        while (!isQuoteEnclosure(c = this.readNext()))
            this.updateQuoteData(c);
        
        this.unread(c);
    }
    
    private void validateEnclosure(char c) throws IOException {
        if( !isQuoteEnclosure(c)) {
            this.unread(c);
            MarkupTag tag = MarkupTag.getTag(c);
            throw new MissingEnclosureParsingException(this.input.getPosition(),
                                                       (tag != null ? tag.toChar() : '?'), 
                                                       this.result);
        }
    }
    
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.setQuoteData(new StringBuilder());
    }
}