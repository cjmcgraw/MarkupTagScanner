package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.mycompany.htmlvalidator.scanners.readers.parsers.errors.EndOfInputParsingException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public abstract class AbstractHtmlParser implements HtmlParser{
    public static final char OPEN_TAG_ENCLOSURE = '<';
    public static final char CLOSE_TAG_ENCLOSURE = '>';
    public static final char QUOTE_TAG_ENCLOSURE = '"';
    private static final Set<Character> TAG_ENCLOSURES = 
            new HashSet<Character>(Arrays.asList(OPEN_TAG_ENCLOSURE, CLOSE_TAG_ENCLOSURE));
    
    public static boolean isOpenTagEnclosure(char c) {
        return c == OPEN_TAG_ENCLOSURE;
    }
    
    public static boolean isCloseTagEnclosure(char c) {
        return c == CLOSE_TAG_ENCLOSURE;
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
    
    protected void unread(char c) throws IOException {
        this.input.unread(c);
    }
    
    protected boolean isEndOfInput(int value) {
        return value == -1;
    }
    
    public void setState(PushbackAndPositionReader input, MutableHtmlData result) {
        this.input = input;
        this.result = result;
    }
    
    public void clearState() {
        this.setState(null, null);
    }
}
