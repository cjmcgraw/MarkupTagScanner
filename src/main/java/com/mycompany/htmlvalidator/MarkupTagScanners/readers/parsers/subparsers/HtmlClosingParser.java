package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.subparsers;

import java.io.IOException;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends HtmlUtilityParser {
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, HtmlData result) throws IOException {
        this.setState(input, result);
        this.readClosingCharLocation();
        this.confirmClosingTag();
        this.clearState();
        return result;
    }
    
    private void readClosingCharLocation() throws IOException {
        this.read();
        this.validateChar();
    }
    
    protected boolean validateChar() throws IOException {
        return super.validateChar(this.getCurrChar());
    }
    
    private void confirmClosingTag() throws IOException {
        boolean isClosing = this.isClosingAttribute();
        if (!isClosing) 
            this.unread();
        this.getResult().setIsClosing(isClosing);
    }
}