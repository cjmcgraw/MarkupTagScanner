package com.mycompany.htmlvalidator.parsers.utilities.mock;

import java.io.IOException;
import java.io.Reader;
import java.nio.CharBuffer;

public class ReaderMock extends Reader {
    private String[] tagData;
    private boolean closed;
    private int currIndex;
    private String stringData;
    private char[] data;
    
    public ReaderMock(String s, String[] tagData) {
        this.stringData = s;
        this.data = s.toCharArray();
        this.tagData = tagData;
        this.currIndex = 0;
    }
    
    @Override
    public void close() {
        this.closed = true;
    }
    
    public boolean isClosed() {
        return this.closed;
    }
    
    public void pointToTag(int index) {
        this.currIndex = this.stringData.indexOf(tagData[index], currIndex);
    }
    
    @Override
    public int read() {
        int value = (int) this.data[this.currIndex];
        this.currIndex++;
        return value;
    }
    
    @Override
    public int read(CharBuffer target) {
        int numRead = 0;
        for(int i = this.currIndex; i < this.data.length; i++) {
            target.append(this.data[i]);
            numRead ++;
        }
        this.currIndex += numRead;
        return numRead;
    }
    
    @Override
    public int read(char[] cbuf, int off, int len) throws IOException {
        int numRead = 0;
        for(int i = this.currIndex; i < this.data.length && i < len; i++) {
            cbuf[off] = this.data[i];
            off++;
            numRead++;
        }
        this.currIndex += numRead;
        return numRead;
    }
    
}
