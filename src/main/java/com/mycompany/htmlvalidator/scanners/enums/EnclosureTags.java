package com.mycompany.htmlvalidator.scanners.enums;

public enum EnclosureTags {
    HTML_ENCLOSURE          (MarkupTag.OPENING_TAG, MarkupTag.CLOSING_TAG),
    SINGLE_QUOTE_ENCLOSURE  (MarkupTag.SINGLE_QUOTE),
    DOUBLE_QUOTE_ENCLOSURE  (MarkupTag.DOUBLE_QUOTE);
    
    private MarkupTag open;
    private MarkupTag close;
    
    private EnclosureTags(MarkupTag open, MarkupTag close) {
        this.open = open;
        this.close = close;
    }
    
    private EnclosureTags(MarkupTag enclosure) {
        this.open = enclosure;
        this.close = enclosure;
    }
    
    public MarkupTag getOpening() {
        return this.open;
    }
    
    public MarkupTag getClosing() {
        return this.close;
    }
    
    public boolean isOpening(MarkupTag tag) {
        return this.open == tag;
    }
    
    public boolean isClosing(MarkupTag tag) {
        return this.close == tag;
    }
}
