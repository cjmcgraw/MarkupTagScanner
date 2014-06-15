package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.AbstractHtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends AbstractHtmlParser {
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
    
    private void validateChar() throws IOException {
        if (isCloseTagEnclosure(this.currChar)) 
            this.closeTagRead();
        else if (isOpenTagEnclosure(this.currChar))
            this.openTagRead();
    }
    
    private void closeTagRead() throws IOException {
        this.unread(this.currChar);
        throw new UnexpectedCloseTagParsingException(this.input.getPosition(), this.result);
    }
    
    private void openTagRead() throws IOException {
        this.unread(this.currChar);
        throw new UnclosedTagParsingException(this.input.getPosition(), this.result);
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