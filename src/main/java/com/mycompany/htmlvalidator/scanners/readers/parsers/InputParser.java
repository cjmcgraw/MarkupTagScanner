package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.awt.Point;
import java.io.*;

import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class InputParser<T> {
    private static final String CLASS_NAME = "InputParser";
    private static final String FIELD_NAME = "input";
    
    protected PushbackAndPositionReader input;
    
    public abstract T parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        this.input = input;
    }
    
    protected void clearState() {
        this.input = null;
    }
    
    protected char read() throws IOException {
       this.validateState();
        int value = this.input.read();
        
        if (value == -1)
            throw new EOFException();
        
        return (char) value;
    }
    
    protected void unread(char ch) throws IOException {
        this.validateState();
        this.input.unread(ch);
    }
    
    protected Point currentPosition() {
        this.validateState();
        return this.input.getPosition();
    }
    
    protected boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }
    
    private void validateState() {
        if (this.isMissingState()) {
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
        }
    }
    
    private boolean isMissingState() {
        return this.input == null;
    }
}
