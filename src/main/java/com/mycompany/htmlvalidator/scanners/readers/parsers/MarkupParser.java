package com.mycompany.htmlvalidator.scanners.readers.parsers;

import com.mycompany.htmlvalidator.scanners.MarkupTag;

public abstract class MarkupParser<T> extends InputParser<T> {
    
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
