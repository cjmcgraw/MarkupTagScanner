package com.mycompany.htmlvalidator.parsers.utilities.mock;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

import com.mycompany.htmlvalidator.parsers.utilities.HtmlReader;

public class HtmlBufferedReaderMock implements HtmlReader {
    private Iterator<String> data;
    private boolean outputException;
    private boolean closed;
    
    public HtmlBufferedReaderMock(Collection<String> readerData) {
        this.data = readerData.iterator();
    }
    
    @Override
    public void close() {this.closed = true;}
    
    public boolean isClosed() {return this.closed;}
    
    public void setOutputException(boolean causesException) {
        this.outputException = causesException;
    }
    
    @Override
    public boolean hasNext() {
        return data.hasNext();
    }

    @Override
    public String next() {
        if(outputException)
            this.exceptionOutput();
        return this.getNext();
    }
    public String getNext() {
       return this.data.next();
    }
    
    public void exceptionOutput() {
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {throw new UnsupportedOperationException();}

    @Override
    public int getCurrentLineNumber() {
        // TODO Auto-generated method stub
        return 0;
    }
}
