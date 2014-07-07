package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.*;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.*;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class DataParser extends InputParser<HtmlData> {
    protected MutableHtmlData result;
    
    protected char readNext() throws IOException {
        try {
            return this.read();
        } catch (EOFException e) {
            throw new EndOfInputParsingException(this.currentPosition(), this.result);
        }
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input) throws IOException;
    
    protected boolean validateChar(char c) throws IOException {
        if(isClosingTag(c))
            this.closeTagRead(c);
        else if (isOpeningTag(c))
            this.openTagRead(c);
        
        return true;
    }
    
    protected void closeTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnexpectedCloseTagParsingException(this.currentPosition(), this.result);
    }
    
    protected void openTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnclosedTagParsingException(this.currentPosition(), this.result);
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        this.setState(input, new MutableHtmlData());
    }
    
    protected void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        super.setState(input);
        this.result = result;
    }
    
    protected void clearState() {
        super.clearState();
        this.result = null;
    }
    
    protected boolean isClosingTag(char c) {
        return MarkupTag.CLOSING_TAG.equals(c);
    }
    
    protected boolean isOpeningTag(char c) {
        return MarkupTag.OPENING_TAG.equals(c);
    }
    
    protected boolean isClosingAttribute(char c) {
        return MarkupTag.CLOSING_ATTRIBUTE.equals(c);
    }
    
    protected boolean isQuoteEnclosure(char c) {
        return MarkupTag.isQuoteEnclosure(c);
    }
    
    protected boolean isTagEnclosure(char c) {
        return MarkupTag.isTagEnclosure(c);
    }
}
