package com.mycompany.htmlvalidator.scanners.readers.utilities;

import java.awt.Point;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.nio.CharBuffer;

public class PushbackAndNewLineConsumerReader implements PushbackAndPositionReader {
    public static String newLine = System.lineSeparator();
    
    private LineNumberReader lineNumReader;
    private PushbackReader pushbackReader;
    
    private int rowCounter;
    
    public PushbackAndNewLineConsumerReader(Reader input) {
        this.lineNumReader = new LineNumberReader(input);
        this.pushbackReader = new PushbackReader(this.lineNumReader);
        rowCounter = 0;
    }
    
    public int read(char[] cbuf) throws IOException {
        return read(cbuf, 0, cbuf.length);
    }
    
    @Override
    public int read(CharBuffer cb) throws IOException {
        char[] preCB = new char[cb.limit()];
        int value = this.read(preCB);
        
        cb.put(preCB);
        return value;
    }
    
    public int skip(int numOfValues) throws IOException {
        int value = 0;
        int i = 0;
        for(; i < numOfValues && value != -1; i++) {
            this.read();
        }
        return i;
    }
    
    public int read(char[] cbuf, int out, int len) throws IOException {
        int value = 0;
        int i = out;
        for(; i < len; i++) {
            value = this.read();
            cbuf[i] = (char) value;
        }
        return i - len;
    }
    
    public int read() throws IOException {
        int lineNum = lineNumReader.getLineNumber();
        int value = this.readNext();
        this.rowCounter += (value != -1) ? 1 : 0;
        
        while (value != -1 && lineNum != this.getLineNumber()) {
            this.rowCounter = 0;
            
            lineNum = this.getLineNumber();
            value = this.readNext();
            this.rowCounter += (value != -1) ? 1 : 0;
        }
        return value;
    }

    public void unread(int value) throws IOException {
        this.pushbackReader.unread(value);
    }
    
    public void unread(char c) throws IOException {
        this.pushbackReader.unread(c);
    }
    
    public Point getPosition() {
        int lineNum = this.getLineNumber();
        return new Point(this.rowCounter, lineNum);
    }

    @Override
    public void close() throws IOException {
        this.lineNumReader.close();
        this.pushbackReader.close();
    }
    
    private int readNext() throws IOException {
        return this.pushbackReader.read();
    }
    
    private int getLineNumber() {
        return this.lineNumReader.getLineNumber();
    }
    
}