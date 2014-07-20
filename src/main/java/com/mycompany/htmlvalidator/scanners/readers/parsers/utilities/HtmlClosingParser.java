package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends HtmlUtilityParser {
    public static final char CLOSING_CHAR = '/';
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
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
        boolean isClosing = this.isClosing();
        if (!isClosing) 
            this.unread();
        this.getResult().setIsClosing(isClosing);
    }
    
    private boolean isClosing() {
        return this.getCurrChar() == CLOSING_CHAR;
    }
}