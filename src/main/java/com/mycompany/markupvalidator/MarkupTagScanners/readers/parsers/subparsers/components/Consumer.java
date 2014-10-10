package com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components;

import java.io.*;

import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.InputParser;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.parsers.subparsers.components.errors.EndOfInputComponentError;
import com.mycompany.markupvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class Consumer extends InputParser<Integer>{
    private int counter;
    
    @Override
    public abstract Integer parse(PushbackAndPositionReader input) throws IOException;
    
    protected Integer getCounter() {
        return this.counter;
    }
    
    @Override
    protected char read() throws IOException{
        try {
            this.counter++;
            return super.read();
        } catch (EOFException e) {
            throw new EndOfInputComponentError("");
        }
    }
    
    @Override
    protected void unread(char c) throws IOException {
        this.counter--;
        super.unread(c);
    }
    
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.counter = 0;
    }
    
    protected void clearState() {
        super.clearState();
        this.counter = 0;
    }
}
