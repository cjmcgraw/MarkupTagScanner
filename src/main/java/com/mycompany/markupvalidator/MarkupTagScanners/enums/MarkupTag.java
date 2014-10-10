package com.mycompany.markupvalidator.MarkupTagScanners.enums;

public enum MarkupTag {
    OPENING_TAG                     ('<'),
    CLOSING_TAG                     ('>'),
    CLOSING_ATTRIBUTE               ('/'),
    DOUBLE_QUOTE                    ('"'),
    SINGLE_QUOTE                    ('\''),
    ATTRIBUTE_VALUE_SEPARATOR       ('=');
    
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
    
    public static MarkupTag getTag(char c) {
        for (MarkupTag tag : MarkupTag.values()) {
            if (tag.equals(c))
                return tag;
        }
        return null;
    }
    
    public static boolean isTagEnclosure(char c) {
        return MarkupTag.OPENING_TAG.equals(c) || MarkupTag.CLOSING_TAG.equals(c);
    }
    
    public static boolean isQuoteEnclosure(char c) {
        return MarkupTag.SINGLE_QUOTE.equals(c) || MarkupTag.DOUBLE_QUOTE.equals(c);
    }
}