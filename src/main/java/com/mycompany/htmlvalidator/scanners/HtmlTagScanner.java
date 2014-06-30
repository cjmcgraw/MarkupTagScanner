package com.mycompany.htmlvalidator.scanners;

import com.mycompany.htmlvalidator.scanners.HtmlScanner;
import com.mycompany.htmlvalidator.scanners.readers.*;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.tokens.*;

import java.io.*;
import java.util.NoSuchElementException;

public class HtmlTagScanner implements HtmlScanner {
    private HtmlReader reader;
    
    public HtmlTagScanner(String data) throws IOException {
        this(new HtmlBufferedReader(data));
    }
    
    public HtmlTagScanner(File file) throws FileNotFoundException, IOException {
        this(new HtmlBufferedReader(file));
    }
    
    public HtmlTagScanner(HtmlReader reader) {
        this.reader = reader;
    }
    
    @Override
    public void close() throws IOException {
        this.reader.close();
    }
    
    @Override
    public HtmlTag next() {
        this.checkNext();
        return this.generateNextHtmlTag(); 
    }
    
    private boolean checkNext() {
        boolean result = this.hasNext();
        
        if (!result)
            throw new NoSuchElementException();
        
        return result;
    }
    
    private HtmlTag generateNextHtmlTag() {
        MutableHtmlTag result = this.makeTag();
        HtmlData data = this.reader.next();
        
        result.setData(data);
        return result;
    }
    
    private MutableHtmlTag makeTag() {
        return new MutableHtmlTag();
    }
    
    @Override
    public boolean hasNext() {
        return this.reader.hasNext();
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException();
    }
}
