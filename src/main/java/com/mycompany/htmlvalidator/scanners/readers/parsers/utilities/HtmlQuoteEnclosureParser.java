package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.MissingEnclosureParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlQuoteEnclosureParser extends HtmlParser {
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
        while (!isQuoteTagEnclosure(c = this.readNext()))
            this.updateQuoteData(c);
        
        this.unread(c);
    }
    
    private void validateEnclosure(char c) throws IOException {
        if( !isQuoteTagEnclosure(c)) {
            this.unread(c);
            throw new MissingEnclosureParsingException(this.input.getPosition(),
                                                       QUOTE_TAG_ENCLOSURE, 
                                                       this.result);
        }
    }
    
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.setQuoteData(new StringBuilder());
    }
}