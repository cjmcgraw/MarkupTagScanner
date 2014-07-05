package com.mycompany.htmlvalidator.scanners.readers.parsers;

import java.awt.Point;
import java.io.*;
import java.util.*;

import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlParser {
    public static final char OPEN_TAG_ENCLOSURE = '<';
    public static final char CLOSE_TAG_ENCLOSURE = '>';
    public static final char SINGLE_QUOTE_TAG_ENCLOSURE = '\'';
    public static final char DOUBLE_QUOTE_TAG_ENCLOSURE = '"';
    public static final char CLOSING_TAG = '/';
    
    public static final Set<Character> TAG_ENCLOSURES = 
            new HashSet<Character>(Arrays.asList(OPEN_TAG_ENCLOSURE, CLOSE_TAG_ENCLOSURE));
    
    public static final Set<Character> QUOTE_ENCLOSURES =
            new HashSet<Character>(Arrays.asList(SINGLE_QUOTE_TAG_ENCLOSURE, 
                                                 DOUBLE_QUOTE_TAG_ENCLOSURE));
    
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
        return c == DOUBLE_QUOTE_TAG_ENCLOSURE;
    }
    
    public static boolean isTagEnclosure(char c) {
        return TAG_ENCLOSURES.contains(c);
    }
    
    protected PushbackAndPositionReader input;
    
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
}
