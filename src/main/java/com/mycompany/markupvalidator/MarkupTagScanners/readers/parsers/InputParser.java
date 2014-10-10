package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers;

import java.awt.Point;
import java.io.*;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.errors.InvalidStateException;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class InputParser<T> {
    private static final String CLASS_NAME = "InputParser";
    private static final String FIRST_FIELD_NAME = "input";
    private static final String SECOND_FIELD_NAME = "currChar";
    
    private static final char EMPTY_CHAR = Character.UNASSIGNED;
    
    protected PushbackAndPositionReader input;
    protected char currChar;
    
    public abstract T parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        this.input = input;
        this.invalidateCurrChar();
    }
    
    protected void clearState() {
        this.input = null;
        this.invalidateCurrChar();
    }
    
    protected char read() throws IOException {
        this.invalidateCurrChar();
        int value = this.getInput().read();
        
        if (value == -1)
            throw new EOFException();
        
        this.currChar = (char) value;
        return this.currChar;
    }
    
    protected void unread(char ch) throws IOException {
        this.invalidateCurrChar();
        this.getInput().unread(ch);
    }
    
    protected void unread() throws IOException {
        this.unread(this.getCurrChar());
    }
    
    protected char peekNextRead() throws IOException {
        char ch = this.read();
        this.unread();
        return ch;
    }
    
    protected Point currentPosition() {
        return this.getInput().getPosition();
    }
    
    protected boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }
    
    protected PushbackAndPositionReader getInput() {
        this.validateState();
        return this.input;
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIRST_FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return this.input == null;
    }
    
    protected char getCurrChar() {
        this.validateCharState();
        return this.currChar;
    }
    
    private void invalidateCurrChar() {
        this.currChar = EMPTY_CHAR;
    }
    
    private void validateCharState() {
        if(this.isMissingCharState())
            throw new InvalidStateException(CLASS_NAME, SECOND_FIELD_NAME);
    }
    
    private boolean isMissingCharState() {
        return this.isMissingState() || this.currChar == EMPTY_CHAR;
    }
}