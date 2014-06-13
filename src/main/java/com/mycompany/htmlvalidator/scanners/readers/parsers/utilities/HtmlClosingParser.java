package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.MutableHtmlData;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser extends HtmlParser {
    public static final char closingChar = '/';
    
    private PushbackAndPositionReader input;
    private MutableHtmlData result;
    
    private char currChar;
    private int value;
    
    @Override
    public HtmlData parse(PushbackAndPositionReader input, MutableHtmlData result) throws IOException {
        this.setState(input, result);
        this.readClosingCharLocation();
        this.confirmClosingTag();
        this.clearState();
        
        return result;
    }
    
    private void readClosingCharLocation() throws IOException {
        this.value = this.input.read();
        this.validateValue();
        this.currChar = (char) this.value;
        this.validateChar();
    }
    
    private void validateValue() {
        if ( this.value == -1)
            throw new EndOfInputParsingException(this.input.getPosition(), this.result);
    }
    
    private void validateChar() {
        if (HtmlParser.isCloseTagEnclosure(this.currChar))
            throw new UnexpectedCloseTagParsingException(this.input.getPosition(), this.result);
        else if (HtmlParser.isOpenTagEnclosure(this.currChar))
            throw new UnclosedTagParsingException(this.input.getPosition(), this.result);
    }
    
    private void confirmClosingTag() throws IOException {
        boolean isClosing = (this.currChar == closingChar);
        if (!isClosing) 
            this.input.unread(this.currChar);
        this.result.setIsClosing(isClosing);
    }
    
    private void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        this.input = input;
        this.result = result;
        
        this.currChar = Character.UNASSIGNED;
        this.value = 0;
    }
    
    private void clearState() {
        setState(null, null);
    }
}
