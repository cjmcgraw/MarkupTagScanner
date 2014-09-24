package com.mycompany.htmlvalidator.MarkupTagScanners.readers.parsers;

import com.mycompany.htmlvalidator.MarkupTagScanners.enums.MarkupTag;

public abstract class MarkupParser<T> extends InputParser<T> {
    
    protected boolean isClosingTag(char c) {
        return MarkupTag.CLOSING_TAG.equals(c);
    }
    
    protected boolean isClosingTag() {
        return this.isClosingTag(this.getCurrChar());
    }
    
    protected boolean isOpeningTag(char c) {
        return MarkupTag.OPENING_TAG.equals(c);
    }
    
    protected boolean isOpeningTag() {
        return this.isOpeningTag(this.getCurrChar());
    }
    
    protected boolean isClosingAttribute(char c) {
        return MarkupTag.CLOSING_ATTRIBUTE.equals(c);
    }
    
    protected boolean isClosingAttribute() {
        return this.isClosingAttribute(this.getCurrChar());
    }
    
    protected boolean isQuoteEnclosure(char c) {
        return MarkupTag.isQuoteEnclosure(c);
    }
    
    protected boolean isQuoteEnclosure() {
        return this.isQuoteEnclosure(this.getCurrChar());
    }
    
    protected boolean isTagEnclosure(char c) {
        return MarkupTag.isTagEnclosure(c);
    }
    
    protected boolean isTagEnclosure() {
        return this.isTagEnclosure(this.getCurrChar());
    }
}
