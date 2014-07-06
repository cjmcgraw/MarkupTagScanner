package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.awt.Point;
import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.MarkupTag;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlParser<T> {
    protected PushbackAndPositionReader input;
    
    public abstract T parse(PushbackAndPositionReader input) throws IOException;
    
    protected void setState(PushbackAndPositionReader input) {
        this.input = input;
    }
    
    protected void clearState() {
        this.input = null;
    }
    
    protected char read() throws IOException {
        int value = this.input.read();
        
        if (value == -1)
            throw new EOFException();
        
        return (char) value;
    }
    
    protected void unread(char ch) throws IOException {
        this.input.unread(ch);
    }
    
    protected Point currentPosition() {
        return this.input.getPosition();
    }
    
    protected boolean isTagEnclosure(char c) {
        return MarkupTag.OPENING_TAG.equals(c) || MarkupTag.CLOSING_TAG.equals(c);
    }
    
    protected boolean isQuoteEnclosure(char c) {
        return MarkupTag.SINGLE_QUOTE.equals(c) || MarkupTag.DOUBLE_QUOTE.equals(c);
    }
    
    protected boolean isOpeningTag(char c) {
        return MarkupTag.OPENING_TAG.equals(c);
    }
    
    protected boolean isClosingTag(char c) {
        return MarkupTag.CLOSING_TAG.equals(c);
    }
    
    protected boolean isClosingAttribute(char c) {
        return MarkupTag.CLOSING_ATTRIBUTE.equals(c);
    }
    
    protected boolean isAttributeValueSeparator(char c) {
        return MarkupTag.ATTRIBUTE_VALUE_SEPARATOR.equals(c);
    }
}
