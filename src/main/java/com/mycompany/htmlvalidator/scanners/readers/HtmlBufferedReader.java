package com.mycompany.htmlvalidator.scanners.readers;

import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.*;

public class HtmlBufferedReader implements HtmlReader{
    private PushbackAndPositionReader reader;
    private boolean emptyReader;
    private DataParser parser;
    
    private HtmlData currData;
    private boolean hasData;
    
    private char currChar;
    
    public HtmlBufferedReader() throws IOException {
        this.parser = new HtmlDataParser();
        this.currData = null;
        this.hasData = false;
    }
    
    public HtmlBufferedReader(DataParser parser) throws IOException {
        this();
        this.parser = parser;
    }
    
    public HtmlBufferedReader(File file) throws FileNotFoundException, IOException {
        this();
        Reader reader = new BufferedReader(new FileReader(file));
        this.setReader(new PushbackAndNewLineConsumerReader(reader));
    }
    
    public HtmlBufferedReader(String data) throws IOException {
        this();
        Reader reader = new BufferedReader(new StringReader(data));
        this.setReader(new PushbackAndNewLineConsumerReader(reader));
    }
    
    public void setReader(PushbackAndPositionReader input) throws IOException {
        this.reader = input;
        this.getNextData();
    }
    
    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public boolean hasNext() {
        return this.hasData;
    }
    
    @Override
    public HtmlData next() {
        this.validateNext();
        HtmlData result = this.currData;
        this.getNextData();
        return result;
    }
    
    private void validateNext() {
        if (this.currData == null || !this.hasData)
            throw new NoSuchElementException();
    }
    
    private void getNextData() {
        try {
            this.readNextData();
        } catch (IOException e) {
            throw new NoSuchElementException(" An error occurred in reading the input! No such element found");
        }
    }
    
    private void readNextData() throws IOException {
        this.clearTempState();
        this.cycleDataToNextTag();
        HtmlData currData = this.parseTagData();
        this.updateState(currData);
    }
    
    private void cycleDataToNextTag() throws IOException {
        while (!this.emptyReader && !isTagEnclosure())
            this.readNext();
        
        if (!this.emptyReader)
            this.unread();
    }
    
    private HtmlData parseTagData() throws IOException {
        if(this.emptyReader)
            return null;
        
        return this.parser.parse(this.reader);
    }
    
    private char readNext() throws IOException {
        int value = this.reader.read();
        this.emptyReader = this.readerIsEmpty(value);
        this.currChar = (char) value;
        return this.currChar;
    }
    
    private void unread() throws IOException {
        this.reader.unread(this.currChar);
    }
    
    private boolean validateCurrData() {
        return this.currData != null;
    }
    
    private boolean readerIsEmpty(int value) {
        return value == -1;
    }
    
    private boolean isTagEnclosure() {
        return MarkupTag.OPENING_TAG.equals(this.currChar) || MarkupTag.CLOSING_TAG.equals(this.currChar);
    }
    
    private void updateState(HtmlData data) {
        this.currData = data;
        this.hasData = validateCurrData();
    }
    
    private void clearTempState() {
        this.currChar = ' ';
    }
    
    @Override
    public void remove() throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Remove is undefined for HtmlReader");
    }
}