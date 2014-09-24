package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers;

import java.io.*;

import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.exceptions.*;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers.tokens.HtmlData;
import com.mycompany.htmlvalidator.MarkupTagScanners.readers.utilities.PushbackAndPositionReader;

public abstract class DataParser extends MarkupParser<HtmlData> {
    private static final String CLASS_NAME = "DataParser";
    private static final String FIELD_NAME = "result";
    protected HtmlData result;
    
    protected char read() throws IOException {
        try {
            return super.read();
        } catch (EOFException e) {
            throw this.generateEndOfInputParsingException();
        }
    }
    
    protected EndOfInputParsingException generateEndOfInputParsingException() {
        return new EndOfInputParsingException(this.currentPosition(), this.getResult());
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input) throws IOException;
    
    protected boolean validateChar(char c) throws IOException {
        if(isClosingTag(c))
            this.closeTagRead(c);
        else if (isOpeningTag(c))
            this.openTagRead(c);
        
        return true;
    }
    
    private void closeTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnexpectedCloseTagParsingException(this.currentPosition(), this.getResult());
    }
    
    private void openTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnclosedTagParsingException(this.currentPosition(), this.getResult());
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        this.setState(input, new HtmlData());
    }
    
    protected void setState(PushbackAndPositionReader input, HtmlData result) {
        super.setState(input);
        this.result = result;
    }
    
    protected void clearState() {
        super.clearState();
        this.result = null;
    }
    
    protected HtmlData getResult() {
        this.validateState();
        return this.result;
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, FIELD_NAME);
    }
    
    private boolean isMissingState() {
        return result == null;
    }
}