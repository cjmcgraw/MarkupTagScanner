package com.mycompany.htmlvalidator.scanners.readers.parsers.utilities.components;

import java.io.IOException;

import com.mycompany.htmlvalidator.scanners.enums.MarkupTagNames;
import com.mycompany.htmlvalidator.scanners.readers.parsers.HtmlAttribute;
import com.mycompany.htmlvalidator.scanners.readers.parsers.exceptions.InvalidStateException;
import com.mycompany.htmlvalidator.scanners.readers.utilities.PushbackAndPositionReader;

public class HtmlCommentAttributeParser extends HtmlComponentAttributeParser {
    private final static String CLASS_NAME = "HtmlCommentAttributeParser";
    private final static String FIRST_FIELD_NAME = "closingTag";
    private final static String SECOND_FIELD_NAME = "data";
    
    private final static String COMMENT_CLOSE = MarkupTagNames.COMMENT_TAG.getEndName();
    
    private StringBuilder closingTag;
    private StringBuilder data;
    
    @Override
    public HtmlAttribute parse(PushbackAndPositionReader input) throws IOException {
        this.setState(input);
        this.readCommentData();
        this.setAttributeData();
        
        HtmlAttribute attribute = this.getAttribute();
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
        return COMMENT_CLOSE.charAt(this.getClosingTag().length());
    }
    
    private void updateClosingTag(char c) {
        this.getClosingTag().append(c);
    }
    
    private void updateAttributeData(char c) {
        this.getData().append(c);
    }
    
    private void setAttributeData() {
        int start = 0;
        int end = this.getData().length() - MarkupTagNames.COMMENT_TAG.getEndName().length();
        this.getAttribute().setName(this.getData().substring(start, end));
    }
    
    private boolean commentDataRemaining() {
        return !this.closingFound();
    }
    
    private boolean closingFound() {
        return this.getClosingTag().toString().equals(COMMENT_CLOSE);
    }
    
    @Override
    protected void setState(PushbackAndPositionReader input) {
        super.setState(input);
        this.data = new StringBuilder();
        this.clearClosingTag();
    }
    
    private void clearClosingTag() {
        this.closingTag = new StringBuilder();
    }
    
    @Override
    protected void clearState() {
        super.clearState();
        this.data = null;
        this.closingTag = null;
    }
    
    @Override
    protected String getAttributeName() {
        return this.getData().toString();
    }
    
    @Override
    protected String getAttributeValue() {
        return "";
    }
    
    private StringBuilder getClosingTag() {
        this.validateState();
        return this.closingTag;
    }
    
    private StringBuilder getData() {
        this.validateState();
        return this.data;
    }
    
    private void validateState() {
        if (this.isMissingState())
            throw new InvalidStateException(CLASS_NAME, this.getMissingFieldName());
    }
    
    private boolean isMissingState() {
        return this.closingTag == null ||
               this.data == null;
    }
    
    private String getMissingFieldName() {
        if (this.closingTag == null)
            return FIRST_FIELD_NAME;
        if (this.data == null)
            return SECOND_FIELD_NAME;
        return "Unknown field is missing!";
    }
}