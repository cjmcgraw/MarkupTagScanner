package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlDataParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.CloseTagEncounteredParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser implements HtmlParser<Boolean> {
    public static final char closingChar = '/';
    
    private PushbackAndPositionReader input;
    private boolean isClosing;
    private char currChar;
    private int value;
    
    @Override
    public Boolean parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.readClosingCharLocation();
        this.checkChar();
        boolean value = this.isClosing;
        this.clearState();
        
        return value;
    }
    
    private void readClosingCharLocation() throws IOException {
        this.value = this.input.read();
        this.validateValue();
        this.currChar = (char) this.value;
        this.validateChar();
    }
    
    private void validateValue() {
        if ( this.value == -1)
            throw new EndOfInputParsingException(this.input.getPosition(), "");
    }
    
    private void validateChar() {
        if (HtmlDataParser.isCloseTagEnclosure(this.currChar))
            throw new CloseTagEncounteredParsingException(this.input.getPosition(), "");
        else if (HtmlDataParser.isOpenTagEnclosure(this.currChar))
            throw new UnclosedTagParsingException(this.input.getPosition(), "");
    }
    
    private void checkChar() throws IOException {
        boolean isClosing = (this.currChar == closingChar);
        if (!isClosing) 
            this.input.unread(this.currChar);
        
        this.isClosing = isClosing;
    }
    
    private void setState(PushbackAndPositionReader input) {
        this.input = input;
        this.isClosing = false;
        this.currChar = Character.UNASSIGNED;
        this.value = 0;
    }
    
    private void clearState() {
        setState(null);
    }
}
