package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends HtmlParser {
    public static final char closingChar = '/';
    
    private char currChar;
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.readClosingCharLocation();
        this.confirmClosingTag();
        this.clearState();
        return result;
    }
    
    private void readClosingCharLocation() throws IOException {
        this.currChar = this.readNext();
        this.validateChar();
    }
    
    protected boolean validateChar() throws IOException {
        return super.validateChar(this.currChar);
    }
    
    private void confirmClosingTag() throws IOException {
        boolean isClosing = this.isClosing();
        if (!isClosing) 
            this.unread(this.currChar);
        this.result.setIsClosing(isClosing);
    }
    
    private boolean isClosing() {
        return this.currChar == closingChar;
    }
    
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.currChar = Character.UNASSIGNED;
    }
}