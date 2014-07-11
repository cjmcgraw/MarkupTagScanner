package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.MarkupTagNames;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlCommentAttributeParser extends HtmlComponentAttributeParser {
    private final static String COMMENT_CLOSE = MarkupTagNames.COMMENT_TAG.getEndName();
    
    private StringBuilder closingTag;
    private StringBuilder data;
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.readCommentData();
        this.setAttributeData();
        
        HtmlAttribute attribute = this.attribute;
        this.clearState();
        return attribute;
    }
    
    private void readCommentData() throws IOException {
        char c = this.read();
        
        while(this.commentDataRemaining()) {
            this.updateCommentData(c);
            c = this.read();
        }
        
        this.unread(c);
    }
    
    private void updateCommentData(char c) {
        if(c == this.getNextExpectedClosingChar())
            this.updateClosingTag(c);
        else
            this.clearClosingTag();
        this.updateAttributeData(c);
    }
    
    private char getNextExpectedClosingChar() {
        return COMMENT_CLOSE.charAt(this.closingTag.length());
    }
    
    private void updateClosingTag(char c) {
        this.closingTag.append(c);
    }
    
    private void clearClosingTag() {
        this.closingTag = new StringBuilder();
    }
    
    private void updateAttributeData(char c) {
        this.data.append(c);
    }
    
    private void setAttributeData() {
        int start = 0;
        int end = this.data.length() - MarkupTagNames.COMMENT_TAG.getEndName().length();
        this.attribute.setName(this.data.substring(start, end));
    }
    
    private boolean commentDataRemaining() {
        return !this.closingFound();
    }
    
    private boolean closingFound() {
        return this.closingTag.toString().equals(COMMENT_CLOSE);
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.data = new StringBuilder();
        this.clearClosingTag();
    }
    
    @Override
    protected void clearState() {
        super.clearState();
        this.data = null;
        this.closingTag = null;
    }
}