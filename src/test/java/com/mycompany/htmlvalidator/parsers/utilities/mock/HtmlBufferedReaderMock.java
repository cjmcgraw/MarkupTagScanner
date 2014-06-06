package com.mycompany.htmlvalidator.parsers.utilities.mock;

import java.util.Collection;
import java.util.Iterator;

import com.mycompany.htmlvalidator.parsers.utilities.HtmlReader;

public class HtmlBufferedReaderMock implements HtmlReader {
    private Iterator<String> data;
    private boolean closed;
    
    public HtmlBufferedReaderMock(Collection<String> readerData) {
        this.data = readerData.iterator();
    }
    
    @Override
    public void close() {this.closed = true;}
    
    public boolean isClosed() {return this.closed;}

    @Override
    public boolean hasNext() {
        return data.hasNext();
    }

    @Override
    public String next() {
       return this.data.next();
    }

    @Override
    public void remove() {throw new UnsupportedOperationException();}
}
