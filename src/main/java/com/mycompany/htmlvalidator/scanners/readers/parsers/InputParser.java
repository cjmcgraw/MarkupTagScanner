package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.awt.Point;
import java.io.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class InputParser<T> {
    protected PushbackAndPositionReader input;
    
    public abstract T parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        this.input = input;
    }
    
    protected void clearState() {
        this.input = null;
    }
    
    protected char read() throws IOException {
        int value = this.input.read();
        
        if (value == -1)
            throw new EOFException();
        
        return (char) value;
    }
    
    protected void unread(char ch) throws IOException {
        this.input.unread(ch);
    }
    
    protected Point currentPosition() {
        return this.input.getPosition();
    }
    
    protected boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }
}
