package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends HtmlUtilityParser {
    private static final String CLASS_NAME = "HtmlClosingParser";
    private static final String FIELD_NAME = "currChar";
    
    public static final char closingChar = '/';
    
    private char currChar = Character.UNASSIGNED;
    
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
            this.unread(this.getCurrChar());
        this.result.setIsClosing(isClosing);
    }
    
    private boolean isClosing() {
        return this.currChar == closingChar;
    }
    
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input, result);
        this.currChar = Character.UNASSIGNED;
    }
    
    private char getCurrChar() {
        this.validateState();
        return this.currChar;
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.currChar == Character.UNASSIGNED;
    }
}