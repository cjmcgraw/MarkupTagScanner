package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlParser;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlClosingParser implements HtmlParser<Boolean> {
    public static final char closingChar = '/';
    
    private PushbackAndPositionReader input;
    private boolean isClosing;
    private char currChar;
    
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
        this.currChar = (char) this.input.read();
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
    }
    
    private void clearState() {
        setState(null);
    }
}
