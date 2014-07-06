package com.mycompany.htmlvalidator.scanners;

import java.util.*;

public enum MarkupTag {
    OPENING_TAG                     ('<'),
    CLOSING_TAG                     ('>'),
    CLOSING_ATTRIBUTE               ('/'),
    DOUBLE_QUOTE                    ('"'),
    SINGLE_QUOTE                    ('\''),
    ATTRIBUTE_VALUE_SEPARATOR       ('=');
    
    private static Map<MarkupTag, MarkupTag> matches = generateMatches();
    
    private static Map<MarkupTag, MarkupTag> generateMatches() {
        Map<MarkupTag, MarkupTag> result = new HashMap<>();
        
        result.put(CLOSING_TAG, OPENING_TAG);
        result.put(OPENING_TAG, CLOSING_TAG);
        result.put(DOUBLE_QUOTE, DOUBLE_QUOTE);
        result.put(SINGLE_QUOTE, SINGLE_QUOTE);
        
        return result;
    }
    
    private char c;
    MarkupTag(char c) {
        this.c = c;
    }
    
    public boolean equals(char c) {
        return this.c == c;
    }
    
    public boolean equals(String s) {
        return this.toString().equals(s);
    }
    
    public String toString() {
        return this.c + "";
    }
    
    public char toChar() {
        return c;
    }
    
    public MarkupTag getCorrespondingTag() {
       return (matches.containsKey(this) ? matches.get(this) : null);
    }
    
    public static MarkupTag getTag(char c) {
        for (MarkupTag tag : MarkupTag.values()) {
            if (tag.equals(c))
                return tag;
        }
        return null;
    }
}