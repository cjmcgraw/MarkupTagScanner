package com.mycompany.htmlvalidator.scanners.readers;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlData;

public class HtmlReaderMock implements MarkupReader {
    private Queue<HtmlData> data;
    private boolean exceptionState;
    private boolean closed;
    
    public HtmlReaderMock(Collection<HtmlData> data, boolean exceptionState) {
        this.data = new LinkedList<>(data);
        this.exceptionState = exceptionState;
        this.closed = false;
    }
    
    public boolean getClosed() {
        return this.closed;
    }

    @Override
    public boolean hasNext() {
        return !this.data.isEmpty();
    }

    @Override
    public HtmlData next(){
        if (this.exceptionState)
            throw new NoSuchElementException();
        return data.remove();
    }

    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void close() throws IOException {
        this.closed = true;
    }
    
}
