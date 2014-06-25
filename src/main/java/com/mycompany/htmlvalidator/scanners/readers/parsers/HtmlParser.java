package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnclosedTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.UnexpectedCloseTagParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class HtmlParser{
    public static final char OPEN_TAG_ENCLOSURE = '<';
    public static final char CLOSE_TAG_ENCLOSURE = '>';
    public static final char QUOTE_TAG_ENCLOSURE = '"';
    public static final char CLOSING_TAG = '/';
    public static final Set<Character> TAG_ENCLOSURES = 
            new HashSet<Character>(Arrays.asList(OPEN_TAG_ENCLOSURE, CLOSE_TAG_ENCLOSURE));
    
    public static boolean isOpenTagEnclosure(char c) {
        return c == OPEN_TAG_ENCLOSURE;
    }
    
    public static boolean isCloseTagEnclosure(char c) {
        return c == CLOSE_TAG_ENCLOSURE;
    }
    
    public static boolean isClosingTag(char c) {
        return c == CLOSING_TAG;
    }
    
    public static boolean isQuoteTagEnclosure(char c) {
        return c == QUOTE_TAG_ENCLOSURE;
    }
    
    public static boolean isTagEnclosure(char c) {
        return TAG_ENCLOSURES.contains(c);
    }
    
    protected PushbackAndPositionReader input;
    protected MutableHtmlData result;
    
    protected char readNext() throws IOException {
        int value = this.input.read();
        
        if (this.isEndOfInput(value))
            throw new EndOfInputParsingException(this.input.getPosition(), 
                                                 this.result);
        return (char) value;
    }
    
    public abstract HtmlData parse(PushbackAndPositionReader input) throws IOException;
    
    protected void unread(char c) throws IOException {
        this.input.unread(c);
    }
    
    protected boolean isEndOfInput(int value) {
        return value == -1;
    }
    
    protected boolean validateChar(char c) throws IOException {
        if(isCloseTagEnclosure(c))
            this.closeTagRead(c);
        else if (isOpenTagEnclosure(c))
            this.openTagRead(c);
        
        return true;
    }
    
    protected void closeTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnexpectedCloseTagParsingException(this.input.getPosition(), this.result);
    }
    
    protected void openTagRead(char c) throws IOException {
        this.unread(c);
        throw new UnclosedTagParsingException(this.input.getPosition(), this.result);
    }
    
    protected void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        this.input = input;
        this.result = result;
    }
    
    protected void clearState() {
        this.setState(null, null);
    }
}
